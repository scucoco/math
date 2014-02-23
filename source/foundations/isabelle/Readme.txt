This folder gives the encoding of Isabelle/HOL and its semantics in terms of ZFC.

isabelle.elf       gives the encodings of Isabelle (i.e., the Pure logic) and HOL as a signature importing Pure
zfc.elf            imports typed set theory from the ZFC folder and defines some additional notions needed for the translation
isabelle-zfc.elf   imports both files and defines the views from Pure and HOL to ZFC
