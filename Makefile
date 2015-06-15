JCC = javac

all: Dehiscent.class

run: ; make clean && make && java Dehiscent

clean: ; find . -name "*.class" -type f -delete

%.class: %.java ; $(JCC) $<
