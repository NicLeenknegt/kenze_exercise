
all:build run

build:
	@./gradlew build

run:
	@./gradlew run ${LDLIBS}

test:
	@./gradlew test
