package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.details.EventDetailFragment
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.participants.ParticipantsFragment
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.posts.PostsFragment

private val TAB_TITLES = arrayOf(
    R.string.detalhes,
    R.string.conversa,
    R.string.participants
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, val eventID: String, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> EventDetailFragment.newInstance(position + 1)
            1 -> PostsFragment.newInstance(position + 1)
            else -> ParticipantsFragment.newInstance(position + 1, eventID)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}
