import internals.Repl

fun hr(num: Int){
    println()
    for(i in 0..num)
        print("-")
    println()
}

fun main(){

    hr(20)
    println("\nWillkommen / Wellcome / Bem-Vindo")
    println("Copyright \u00A9 2025 Mateus Felipe da Silveira Vieira, Rayana Pimentel Lopes")
    hr(20)

    val instance : Repl = Repl()
    instance.run()
}