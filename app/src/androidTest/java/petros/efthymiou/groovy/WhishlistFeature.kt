package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WhishlistFeature : BaseUITest() {

    @Test
    fun displaysPlaylistTitle() {

        onView(withId(R.id.wishlistFragment))
            .perform(click())

        assertDisplayed("Playlists")
    }

    @Test
    fun displaysListOfWishlist() {

        onView(withId(R.id.wishlistFragment))
            .perform(click())

        // check the quantity of recyclerView items
        assertRecyclerViewItemCount(R.id.wishlist_list, 1)

        // get nth itemView from recyclerView list
        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.wishlist_list), 0))
            )
        )
            .check(ViewAssertions.matches(withText("Hard Rock Cafe")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_category),
                isDescendantOfA(nthChildOf(withId(R.id.wishlist_list), 0))
            )
        )
            .check(ViewAssertions.matches(withText("rock")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.wishlist_list), 0))
            )
        )
            .check(ViewAssertions.matches(withDrawable(R.mipmap.rock)))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen() {

        onView(withId(R.id.wishlistFragment))
            .perform(click())

        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.wishlist_list), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.playlist_details_root)
    }
}