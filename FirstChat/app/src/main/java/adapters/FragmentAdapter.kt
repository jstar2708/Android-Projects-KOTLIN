package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fragments.CallsFragment
import fragments.ChatsFragment
import fragments.GlanceFragment

class FragmentAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> ChatsFragment.newInstance()
            1-> GlanceFragment.newInstance()
            else-> CallsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "CHATS"
            1-> "GLANCE"
            else-> "CALLS"
        }
    }
}