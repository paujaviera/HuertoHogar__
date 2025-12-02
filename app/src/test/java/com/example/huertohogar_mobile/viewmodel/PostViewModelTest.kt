package com.example.huertohogar_mobile.viewmodel

import com.example.huertohogar_mobile.data.model.Post
import com.example.huertohogar_mobile.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    private lateinit var viewModel: PostViewModel
    // Creamos un mock
    private val repository: PostRepository = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        // Configuramos para pruebas
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al iniciar, debe cargar la lista de posts exitosamente`() = runTest {
        // 1. Simulamos que la API devuelve estos datos
        val listaFalsa = listOf(
            Post(1, 1, "Consejo 1", "Riega tus plantas de noche"),
            Post(1, 2, "Consejo 2", "Usa abono orgánico")
        )

        // Entrenamos al mock
        coEvery { repository.getPosts() } returns listaFalsa

        // Iniciamos el ViewModel con el repositorio falso
        viewModel = PostViewModel(repository)

        // Verificamos que el estado se actualizó correctamente
        val estado = viewModel.uiState.value

        // Verificamos que ya no esté cargando
        assertFalse("El estado de carga debería ser false", estado.isLoading)

        // Verificamos que haya cargado los 2 consejos
        assertEquals("Debería haber 2 posts en la lista", 2, estado.posts.size)

        // Verificamos que el primer consejo sea el correcto
        assertEquals("El título no coincide", "Consejo 1", estado.posts[0].title)
    }
}