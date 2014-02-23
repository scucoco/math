set MMT=C:/frabe/MMT/deploy
java -Xmx1024m -cp %MMT%/lib/*;%MMT%/lfcatalog/*;%MMT%/mmt/* info.kwarc.mmt.api.frontend.Run file startup.msl
