import java.io.File

data class Matrix(
    val matrix:Array<ArrayList<String>>,
    var row:Int = 0,
    var column:Int = 0
)

fun main(args: Array<String>) {
    var words:Array<String> = splitStringByDelimiter(readFile("./input.txt"), "\n")

    printStringArray(words)

    var maxWordLength = words.map{ it:String -> it.length }.max() ?: 0
    var maxWord = words.maxBy{ it:String -> it.length }
    println(maxWordLength)
    println(maxWord)

    var wordMatrix: Array<ArrayList<String>> = Array<ArrayList<String>>(maxWordLength) { arrayListOf() }

    //Counting sort
    for (word in words) {
        wordMatrix[word.length-1].add(word)
    }

    for (array in wordMatrix) {
        println("ARRAY")
        for (word in array) {
            println(word)
        }
    }

    var wordMatrixClass:Matrix = Matrix(wordMatrix)
}

fun readFile(filename:String):String = File(filename).inputStream().readBytes().toString(Charsets.UTF_8)

fun splitStringByDelimiter(input:String, delimiter:String):Array<String> = input.split(delimiter).map { it -> it.trim()}.dropLast(1).toTypedArray()

fun printStringArray(stringArray:Array<String>) {
    for (string in stringArray) {
        println(string)
    }
}

fun isSolutionValid(wordLength:Int, solutionLength:Int, solutionWord:String, referenceWord:String):Boolean = ((wordLength + solutionLength) <= referenceWord.length ) && referenceWord.startsWith(solutionWord)

fun isSolutionComplete(referenceWord:String, solutionWord:String):Boolean = referenceWord == solutionWord

fun buildMatchingWordArrayRec(wordMatrix:Matrix, solutionArrayList:ArrayList<String>, solutionLength:Int, solutionCalculation:String, solutionWord:String, referenceWord:String) {

    if (!isSolutionValid(wordMatrix.row, solutionLength, solutionWord, referenceWord)) {
        return
    }

    if (isSolutionComplete(referenceWord, solutionWord)) {
        println(solutionWord)
        solutionArrayList.add(solutionWord)
    }

    while (wordMatrix.row < wordMatrix.matrix.size) {
        while(wordMatrix.column < wordMatrix.matrix[wordMatrix.row].size) {

            //Add current word to word solution
            solutionWord.plus(wordMatrix.matrix[wordMatrix.row][wordMatrix.column])
            //solutionLength is now equal to the original length + matrixRow
            buildMatchingWordArrayRec(wordMatrix, solutionArrayList, solutionLength + wordMatrix.row, solutionCalculation, solutionWord, referenceWord)

            wordMatrix.column + 1
        }
        wordMatrix.row + 1
        wordMatrix.column = 0
    }
}
