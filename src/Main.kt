import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

suspend fun main() {
    var persons:MutableList<String> = mutableListOf()
    var phones:MutableList<String> = mutableListOf()

    measureTimeMillis {
        withContext(newSingleThreadContext("product_thread_context")) {
            launch {
                getPersonsFlow().collect{ i ->
                    persons.addLast(i.toString())
                }
            }

            launch {
                getPhoneFlow(personsMain.size).collect{ i ->
                    phones.addLast(i)
                }
            }
        }
    }

    var personsInfo: MutableList<String> = mutableListOf()
    for (i in phones.indices)
        personsInfo.addLast(persons.get(i)+", "+phones.get(i))

    println("Выводим результат:")
    println(personsInfo.toString())
}


fun getPhoneFlow(length: Int) = flow {
    for (i in 0..length-1) {
        delay(100L)

        val phone: String = "+7917"+(1000000..9999999).random().toString()
        emit(phone)
    }
}

fun getPersonsFlow() = personsMain.asFlow().onEach { delay(100L) }

val personsMain = listOf(
    Person("Иван", "developer"),
    Person("Петр", "engineer"),
    Person("Дмитрий", "doctor"),
    Person("София", "military")
)

class Person(private val name: String, private val role: String) {
    override fun toString(): String {
        return "Пользователь: $name, $role"
    }
}






