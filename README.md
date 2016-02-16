# Sequence Filter Help #

Biological sequence filter based on regular expression. Sequences can be filtered using regular expression patterns formatted following java standard (for additional information
see [this link](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html "Java Patterns")). It uses the [Jillion](http://jillion.sourceforge.net/wiki/index.php/Main_Page "Jillion Home") 
Java library to provide full supports for multiple sequence file formats, such as: fasta, fastq and sff.


## Installation ##

### Dependecies ###

1. Maven
2. Java 1.8+
3. Jillion 5.1
4. JOpt Simple 4.9

This project uses [Maven](https://maven.apache.org/ "Maven Home") as build system so if you want to compile it from scratch you have to install Maven on your system following
[these](https://maven.apache.org/install.html "Maven Install") instructions. However, a precompiled version of the  project is available in the target directory of the github repository.

If you want to compile an executable jar file from scratch using Maven, you have to add Jillion library to your local Maven repository before attempting to compile the jar. 
For additional details please read the information reported below.

### Maven Build ###

First of all you need to take care of the project dependencies. Jopt Simple has its own Maven repository and you shouldn't worry about it. Indeed, Maven should automatically detect the
correct version of the library and include it into the compiled jar. The Jillion library, by contrast, needs to be added to your local Maven database as it is not present in the official
Maven repository. To do so, you have to download Jillion library 5.1 jar file from [here](https://sourceforge.net/projects/jillion/files/5.1/jillion-5.1.jar/download "download"), then open 
a terminal and type:
```
mvn install:install-file -Dfile=<path-to-file> -DgroupId=org.jcvi.jillion -DartifactId=jillion -Dversion=5.1 -Dpackaging=jar
```
replacing ```<path-to-file>``` with the path to the downloaded jar file. This will install the Jillion library into your local Maven repository so you can remove the downloaded jar 
from your hard drive. At this point, you need to clone the [github repository](https://github.com/GiBacci/sequence-filter "repo") on you hard drive using git or downloading it by
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
When Maven has finished you should have your jar file in the traget directory (the runnable jar is the one without the "original" prefix). Copy the generated jar into the desired diretory and
you can get rid of the cloned repository directory. Now you can move into the jar folder and type:
```
java -jar <generated_jar> --help
```
relacing ```<generated_jar>``` with the name of the Maven generated jar file, to test your installation.

### Precompiled JAR ###

You can download a precompiled version of Sequence Filter directly from github repository and unpack it into a folder of your choice. To run the precompiled version of Sequence Filter 
you need to download both Jillion and JOpt Simple libraries. You can download Jillion library using the link provided in the chapter above (Maven Build) whereas you can download 
JOpt Simple from [here](http://repo1.maven.org/maven2/net/sf/jopt-simple/jopt-simple/4.9/jopt-simple-4.9.jar "download"). Once downloaded, place the two jar files into the library folder
called ```sequence-filter-0.0.1-SNAPSHOT_lib```. The jar files must be named as follows (case sensitive):
* Jillion library -> jillion-5.1.jar
* JOpt Simple     -> jopt-simple-4.9.jar
Now you can test your installation by typing ```java -jar sequence-filter-0.0.1-SNAPSHOT.jar --help``` from inside the project folder (where the jar file is).

## Usage ##

Sequence filter is a filter for biological sequences based on regular expression. You can achieve a lot of task using regular pattern filtering sequences based on either id or the sequence
itself. Basically, Sequence Filter accepts two kind of patterns, the "any" patterns and the "all" patterns. A biological sequence has to satisfy at least one ("any" type) or all ("all" type)
the given patterns to be written in the output file. You can combine more patterns to filter senquences based on a virtually unlimited number of features.

### Examples ###

More to come...
