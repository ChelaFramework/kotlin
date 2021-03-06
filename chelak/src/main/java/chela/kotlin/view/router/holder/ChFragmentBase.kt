package chela.kotlin.view.router.holder

import androidx.fragment.app.FragmentManager

class ChFragmentBase: ChHolderBase<ChFragmentInfo>(){
    lateinit var manager:FragmentManager
    var container:Int = 0
    override fun add(holder: ChHolder<ChFragmentInfo>, isBottom:Boolean) = with(create(holder)) {
        val ft = manager.beginTransaction()
        if(transition != 0) ft.setTransition(transition)
        if(style != 0) ft.setTransitionStyle(style)
        when(animation.size){
            2->ft.setCustomAnimations(animation[0], animation[1])
            4->ft.setCustomAnimations(animation[0], animation[1], animation[2], animation[3])
        }
        ft.add(container, fragment, tag)
        ft.addToBackStack(backStack)
        ft.commitAllowingStateLoss()
        manager.executePendingTransactions()
        Unit
    }
    override fun remove(holder: ChHolder<ChFragmentInfo>, t:ChFragmentInfo){
        manager.popBackStackImmediate()
    }
}