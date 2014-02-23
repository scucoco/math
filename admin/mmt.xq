module namespace mmt = "http://omdoc.org/ns";
import module namespace tnt = 'http://tntbase.mathweb.org/ns';
declare namespace abox = "http://omdoc.org/abox";

(:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::)
(: TNTBase interface functions :)

(: should be replaced by
tnt:typed-collection($path as xs:string, $type as xs:string+)
tnt:typed-doc($path as xs:string, $type as xs:string+) as document-node()?
use "*", "?" and "//" in the collection path
:)

(: retrieves a directory from the repository as an XML element that lists the content :)
(: declare function mmt:dir($path as xs:string) as element() {
  tnt:doc-by-key(tnt:get-fs-doc-name($path))
};
This function works if put literally into a query.
But it seems that if my module uses a function that was imported from the tnt module, and then a query uses my module, it yields an error.
:)
(: retrieves a path in the repository
   returns <error/> if not found:)
declare function mmt:doc($path as xs:string) as element() {
  let $doc := collection('xml_content.dbxml')[dbxml:metadata('tnt:path') = $path][dbxml:metadata('tnt:revnum') = -1]/mmt:omdoc
  return if (fn:exists($doc)) then $doc else <error/>
};
(: retrieves a path in the extracted interfaces :)
declare function mmt:output_doc($method as xs:string, $path as xs:string) as document-node() {
  collection('xml_content.dbxml')[dbxml:metadata('tnt:type')=$method][dbxml:metadata('tnt:path')=$path]
};
(: retrieves the whole collection of extracted interfaces :)
declare function mmt:output_collection($method as xs:string) as document-node()* {
  collection('xml_content.dbxml')[dbxml:metadata('tnt:type')=$method]
};

(:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::)
(: MMT-specific functions :)

(: splits an MMTURI http://cds.omdoc.org/doc?mod?sym into (doc, mod, sym)
   return sequence of length 1, 2, or 3 :)
declare function mmt:split($uri as xs:string) as xs:string* {
  fn:tokenize(fn:substring-after($uri, "http://cds.omdoc.org"), "\?")
};

(: adds a base attribute base="$base" to $elem if not already present :)
declare function mmt:addbase($elem as element(), $base as xs:string) as element() {
  if (fn:exists($elem/@base))
  then $elem
  else element {fn:node-name($elem)}
               { attribute {"base"} {$base},
                 $elem/@*,
                 $elem/node()
               }
};

(: dereferences an MMTURI (by translating it to XPath), adds base attribute
   TODO: skip omdoc elements :)
declare function mmt:lookup($mmturi as xs:string) as element() {
  let $parts := mmt:split($mmturi),
      $count := fn:count($parts),
      $doc   := mmt:doc($parts[1])
  return
          if ($count = 3)
    then 
       let $sym := $doc/*[@name=$parts[2]]/*[@name=$parts[3]]
       return if (fn:empty($sym)) then <error mmturi="{$mmturi}"/>
               else mmt:addbase($sym, fn:concat($parts[1], "?", $parts[2]))
    else if ($count = 2)
    then 
       let $thy := $doc/*[@name=$parts[2]]
       return if (fn:empty($thy)) then <error mmturi="{$mmturi}"/>
               else mmt:addbase($thy, $parts[1])
    else if ($count = 1)
    then $doc
    else <error mmturi="{$mmturi}"/>
};

(: obtain the type of an MMTURI :)
declare function mmt:lookupType($mmturi as xs:string) as xs:string {
  let $p := mmt:output_collection("mmt")/abox:mmtabox/abox:individual[@path=$mmturi]/@predicate
  return if (fn:count($p) = 1) then $p[1] else ""
};

(: an auxiliary function for the recursion in mmt:imports :)
declare function mmt:transClose($graph as element()*, $tovisit as xs:string*, $visited as xs:string*) as xs:string* {
  if (empty($tovisit))
  then $visited
  else let $to   := $tovisit[1],
            $from := $graph[@subject = $to]/@object[not(.=$visited)]  
        return mmt:transClose($graph, ($from, $tovisit[not(.=($to,$from))]), ($visited, $to))
};

(: computes the reflexive-transitive closure of the imports-relation between MMTURIs
     depth-first, parents before children, no duplicates
:)
declare function mmt:imports($to as xs:string) as xs:string* {
   let $graph := mmt:output_collection("mmt")/abox:mmtabox/abox:relation[@predicate='HasOccurrenceOfInImport']
   return mmt:transClose($graph, $to, ())
};

(: takes a theory or document URI and returns all necessary notations for it
   notations are from theories that are (transitive-reflexively) imported into theories declared in the argument
   all notations have an a base attribute giving their containing theory 
:)
declare function mmt:getNotations($dec as xs:string) as element() {
  let $tp := mmt:lookupType($dec)
  return element mmt:omdoc {
    if ($tp = "IsDocument" or $tp = "IsTheory") then
       let $graph := mmt:output_collection("mmt")/abox:mmtabox/abox:relation[@predicate=('Declares','HasOccurrenceOfInImport')]
       for $thy in mmt:transClose($graph, $dec, ())
       return for $not in mmt:lookup($thy)/mmt:notation
               return mmt:addbase($not, $thy)
    else ()
  }
};

declare function mmt:getAssertionsModuleLevel() as element()* {
   (mmt:output_collection("mmt")/abox:mmtabox/*[@predicate =
      ('HasOccurrenceOfInImport','HasMeta','HasDomain','HasCodomain','Declares')],
    mmt:output_collection("mmt")/abox:mmtabox/abox:individual)
};

declare function mmt:getAssertions($dec as xs:string) as element()* {
   for $i in mmt:output_collection("mmt")/abox:mmtabox/abox:relation[@object=$dec]
   return ($i, mmt:output_collection("mmt")/abox:mmtabox/abox:relation[@subject=$i/@subject],
                mmt:output_collection("mmt")/abox:mmtabox/abox:individual[@path=$i/@subject])
};

(: takes a theory or document URI and returns the closure (currenty only all imported theories)
:)
declare function mmt:getClosure($dec as xs:string) as element() {
  element mmt:omdoc {
    for $from in 
        for $thy in ($dec, mmt:output_collection("mmt")/abox:mmtabox/abox:relation[@predicate='Declares'][@subject=$dec]/@object)
        return mmt:imports($thy)
    return mmt:lookup($from)
  }
};

(: temporary, for VDs :)
declare function mmt:rawlookup($mmturi as xs:string) as element() {
  let $parts := mmt:split($mmturi),
      $count := fn:count($parts),
      $doc   := mmt:doc($parts[1])
  return
          if ($count = 3)
    then $doc/*[@name=$parts[2]]/*[@name=$parts[3]]
    else if ($count = 2)
    then $doc/*[@name=$parts[2]]
    else if ($count = 1)
    then $doc
    else <error mmturi="{$mmturi}"/>
};

declare function mmt:getRawClosure($dec as xs:string) as element()* {
    for $from in mmt:imports($dec)
    return mmt:rawlookup($from)
};

(:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::)
(: some test cases
mmt:split($from)[1]
mmt:lookup("http://cds.omdoc.org/logics/first-order/model_theory/fol.omdoc?FOLMOD")
mmt:imports("http://cds.omdoc.org/logics/first-order/model_theory/fol.omdoc?FOLMOD")
mmt:getNotations("http://cds.omdoc.org/logics/first-order/model_theory/fol.omdoc?FOLMOD")

:)