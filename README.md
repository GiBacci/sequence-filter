# Sequence Filter Help #

Biological sequence filter based on regular expression. Sequences can be filtered using regular expression patterns formatted following java standard (for additional information
see [this link](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html "Java Patterns")). It uses the [Jillion](http://jillion.sourceforge.net/wiki/index.php/Main_Page "Jillion Home") 
Java library to provide full supports for multiple sequence file formats, such as: fasta, fastq and sff.


## Installation ##

Sequence Filter uses [Maven](https://maven.apache.org/ "Maven Home") as build system so if you want to compile it from scratch you have to install Maven on your system following
[these](https://maven.apache.org/install.html "Maven Install") instructions. However, a precompiled version of the  project is available in the target directory of the github repository.

### Maven Build ###

To build Sequence Filter using Maven you need to clone the [github repository](https://github.com/GiBacci/sequence-filter "repo") on you hard drive using git or downloading it by
clicking the "Download ZIP" button in the upper right corner. To clone the repository using git open the terminal (for Mac and Linux users) or the command prompt (for Windows users) and 
change the current working directory to the location where you want the cloned directory to be made. Next, type:
```
git clone https://github.com/GiBacci/sequence-filter
``` 
and press enter. The current repository will be copied to your hard drive ready to be built using Maven. Otherwise, once the zip file has been downloaded from github, unzip it and move to the
destination folder using your terminal (or the command prompt if you are a Windows user). Make sure that the file ```pom.xml``` is in your working directory and type:
```
mvn package
```
. When Maven have finished the compile step you should have your jar file in the traget directory (the runnable jar is the one without the "original" prefix).




