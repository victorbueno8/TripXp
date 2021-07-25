package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.MyTripsFragment
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class PostsFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyPostsRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            MyTripsFragment().apply {
                arguments = Bundle().apply {
//                    putInt(br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.ARG_SECTION_NUMBER, sectionNumber)
                    //putParcelableArrayList(ARG_TRIP_LIST, tripList)
                }
            }
    }
}