import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.example.wordsapp.Fragment.LetterListFragment
import com.example.wordsapp.R
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @Test
    fun navigate_to_words_nav_component(){

        //ナビゲーション コントローラのテスト インスタンスを作成
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        //LetterListFragment を起動することを指定
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(
            themeResId = R.style.Theme_Words
        )

        //ナビゲーション コントローラが起動済みのフラグメントに使用するナビゲーション グラフを明示的に宣言
        letterListScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //ナビゲーションを促すイベントをトリガー
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))


        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}