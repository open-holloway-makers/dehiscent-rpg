JCC = javac

all: Dehiscent.class

run: ; java Dehiscent

clean: ; find . -name "*.class" -type f -delete

%.class: %.java ; $(JCC) $<
