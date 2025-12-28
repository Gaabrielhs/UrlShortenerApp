package com.example.urlshortenerapp.data.repository

import com.example.urlshortenerapp.data.models.PostLinkResponseBuilder
import com.example.urlshortenerapp.data.service.FakeUrlShortenerService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UrlShortenerRepositoryImplTest {

    private val fakeService = FakeUrlShortenerService()

    @Test
    fun `GIVEN initial state WHEN getHistory THEN should be empty`() {
        val repository = createRepository()

        assertTrue(repository.getHistory().value.isEmpty())
    }

    @Test
    fun `GIVEN success on service WHEN doShort THEN should call service`() = runTest {
        val url = "http://url-example.com"
        val repository = createRepository()

        val result = repository.doShort(url)

        assertTrue(result.isSuccess)
        assertEquals(1, fakeService.postLinkCalled)
        assertEquals(url, fakeService.postLinkParameter)
    }

    @Test
    fun `GIVEN success on service WHEN doShort THEN should add it on history`() = runTest {
        val url = "http://url-example.com"
        val expectedResponse = PostLinkResponseBuilder.createDefault()
        fakeService.nextResult = Result.success(expectedResponse)

        val repository = createRepository()

        repository.doShort(url)

        val history = repository.getHistory().value
        val firstHistory = history.first()
        assertEquals(1, history.size)
        assertEquals(expectedResponse.alias, firstHistory.alias)
    }

    @Test
    fun `GIVEN error on service WHEN doShort THEN should not add it on history`() = runTest {
        val url = "http://url-example.com"
        fakeService.nextResult = Result.failure(Exception("Any error"))
        val repository = createRepository()

        repository.doShort(url)

        val history = repository.getHistory().value
        assertTrue(history.isEmpty())
    }

    private fun createRepository() = UrlShortenerRepositoryImpl(fakeService)
}
