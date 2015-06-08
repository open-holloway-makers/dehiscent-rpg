JCC = javac

all: Dehiscent.class

run: ; java Dehiscent

clean: ; rm -rf *.class

%.class: %.java ; $(JCC) $<
