call mvn install:install-file -Dfile=lib/minim.jar -DgroupId=com.github -DartifactId=minim -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/jsminim.jar -DgroupId=com.github -DartifactId=jsminim -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/jl1.0.jar -DgroupId=com.github -DartifactId=jl -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/mp3spi1.9.4.jar -DgroupId=com.github -DartifactId=mp3spi1 -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/tritonus_aos.jar -DgroupId=com.github -DartifactId=tritonus_aos -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/tritonus_share.jar -DgroupId=com.github -DartifactId=tritonus_share -Dversion=2.2 -Dpackaging=jar

pause