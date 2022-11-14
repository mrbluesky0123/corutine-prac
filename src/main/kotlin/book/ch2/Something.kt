package book.ch2

import kotlinx.coroutines.newSingleThreadContext

class Something {
    val netDispatcher = newSingleThreadContext(name = "ServiceCall")
}