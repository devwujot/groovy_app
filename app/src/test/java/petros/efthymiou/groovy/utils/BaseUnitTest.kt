package petros.efthymiou.groovy.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import petros.efthymiou.groovy.repository.PlaylistRepository
import java.lang.RuntimeException

abstract class BaseUnitTest {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val repository: PlaylistRepository = mock()
    val exception = RuntimeException("Something went wrong")
}