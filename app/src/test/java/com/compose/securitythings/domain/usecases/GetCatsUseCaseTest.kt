package com.compose.securitythings.domain.usecases


import com.compose.securitythings.data.UiState
import com.compose.securitythings.data.model.CatsItemModel
import com.compose.securitythings.data.repo.CatsRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetCatsUseCaseTest {
    private lateinit var useCase: GetCatsUseCase  // System Under Test (SUT)
    private val repository= mockk<CatsRepository>() // Mocked Repository

    @Before
    fun setUp() {
        useCase=GetCatsUseCase(repository) // Initialize the use case before each test

    }


    @Test
    fun `invoke emits SUCCESS when API returns list of cats`()= runTest {
        //AAA

        //Arrange
        val mockCat:List<CatsItemModel> = listOf(mockk()) // Mock a list of cat models
        val response = mockk<Response<List<CatsItemModel>>>{
            every { isSuccessful } returns true // Simulating a successful API call
            every { body() } returns mockCat // Returning mock data
        }
        coEvery { repository.getCats(any(),any()) } returns response // Mock repository call

        //Action
        val resultFlow=useCase(10,1).toList() // Collect all emitted values

        //Assert
        assertEquals(UiState.LOADING,resultFlow[0])  // First emission should be LOADING
        assertEquals(UiState.SUCCESS(mockCat), resultFlow[1]) // Second emission should be SUCCESS with data


        coVerify {
            repository.getCats(10,1) // Ensure repository.getCats() was called with correct parameters

        }


    }

    @Test
    fun `invoke emits SUCCESS with empty list When API returns empty body`()= runTest {
        //Arrange
        val mockCat:List<CatsItemModel> = emptyList()
        val response = mockk <Response<List<CatsItemModel>>>{
            every { isSuccessful } returns true
            every { body() } returns emptyList()
        }
        coEvery { repository.getCats(any(),any()) } returns response

        //Action
        val resultFlow = useCase(10,1).toList()

        //Assert
        assertEquals(UiState.LOADING,resultFlow[0])
        assertEquals(UiState.SUCCESS(mockCat),resultFlow[1])
        //assert(resultFlow[1] is UiState.ERROR) // Ensures error is emitted



        coVerify { repository.getCats(10,1) }


    }


    @Test
    fun `invoke emits ERROR when API call is Error`()= runTest {

        //Arrange

        val response= mockk<Response<List<CatsItemModel>>>{
            every { isSuccessful } returns false
            every { errorBody() } returns mockk()
        }

        coEvery { repository.getCats(any(),any())  } returns response

        // Action
        val reultFlow = useCase(10,1).toList()

        //Assert
        assertEquals(UiState.LOADING,reultFlow[0])
        assert(reultFlow[1] is UiState.ERROR)


        coVerify { repository.getCats(10, 1) }
    }



    @After
    fun tearDown() {
        clearAllMocks() // Clears all MockK mocks after each test
    }
}