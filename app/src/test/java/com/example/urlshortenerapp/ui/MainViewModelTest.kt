package com.example.urlshortenerapp.ui

import com.example.urlshortenerapp.data.models.PostLinkResponse
import com.example.urlshortenerapp.data.repository.FakeUrlShortenerRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private var fakeRepository = FakeUrlShortenerRepository()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN the initial state WHEN view model is created THEN state status should be Done`() = runTest {
        val viewModel = createViewModel()

        assertSame(LinksListState.Status.Done, viewModel.linkListState.value.status)
    }

    @Test
    fun `GIVEN the initial state WHEN view model is created THEN input should be empty`() = runTest {
        val viewModel = createViewModel()

        assertSame("", viewModel.linkListState.value.inputValue)
    }

    @Test
    fun `GIVEN some history WHEN view model is created THEN history should be equals`() = runTest {
        val expected = PostLinkResponse(
            alias = "alias",
            links = PostLinkResponse.Links(
                self = "self",
                short = "short"
            )
        )
        fakeRepository.history.value = listOf(expected)

        val viewModel = createViewModel()

        val history = viewModel.linkListState.value.history
        assertEquals(1, history.size)

        val firstHistory = history.first()
        assertEquals(expected.alias, firstHistory.id)
        assertEquals(expected.links.self, firstHistory.full)
        assertEquals(expected.links.short, firstHistory.short)
    }

    @Test
    fun `GIVEN an input WHEN onInputValueUpdate THEN state input value should be equals`() = runTest {
        val viewModel = createViewModel()
        val expected = "input"

        viewModel.onInputValueChanged(expected)

        assertEquals(expected, viewModel.linkListState.value.inputValue)
    }

    @Test
    fun `GIVEN no input WHEN onCommit THEN state status should be Error`() = runTest {
        val viewModel = createViewModel()

        viewModel.onCommit()

        assertTrue(viewModel.linkListState.value.status.isError)
    }

    @Test
    fun `GIVEN some input WHEN onCommit THEN state status should change accordingly`() = runTest {
        val url = "http://url-example.com"
        val viewModel = createViewModel()

        viewModel.onInputValueChanged(url)
        viewModel.onCommit()
        advanceUntilIdle()

        assertSame(LinksListState.Status.Done, viewModel.linkListState.value.status)
        assertEquals(1, fakeRepository.doShortCalled)
        assertEquals(url, fakeRepository.doShortParameter)
    }

    @Test
    fun `GIVEN some input and error on repository WHEN onCommit THEN state status should change accordingly`() =
        runTest {
            val url = "http://url-example.com"
            val viewModel = createViewModel()
            fakeRepository.nextResponse = Result.failure(Exception("Any failure"))

            viewModel.onInputValueChanged(url)
            viewModel.onCommit()
            advanceUntilIdle()

            assertTrue(viewModel.linkListState.value.status is LinksListState.Status.Error)
            assertEquals(1, fakeRepository.doShortCalled)
            assertEquals(url, fakeRepository.doShortParameter)
        }

    private fun createViewModel(): MainViewModel = MainViewModel(fakeRepository, SharingStarted.Eagerly)
}
