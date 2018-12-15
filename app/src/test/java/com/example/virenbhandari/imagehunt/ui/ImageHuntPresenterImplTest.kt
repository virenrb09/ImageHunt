package com.example.virenbhandari.imagehunt.ui

import com.example.virenbhandari.imagehunt.model.SearchResult
import com.example.virenbhandari.imagehunt.rest.FlickrRepo
import com.example.virenbhandari.imagehunt.util.FlickrUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageHuntPresenterImplTest {

    private lateinit var vm: ImageHuntPresenterImpl

    @Mock
    lateinit var view: ImageHuntView

    @Mock
    lateinit var repo: FlickrRepo

    @Mock
    lateinit var util: FlickrUtil
    @Mock
    lateinit var recyclerAdapterView: RecyclerAdapterView

    @Before
    fun setup() {
        vm = ImageHuntPresenterImpl(view, repo, util)
        vm.adapter = recyclerAdapterView
    }

    @Test
    fun loadMore_EmptySearchTerm() {
        vm.searchTerm = EMPTY_STRING
        vm.loadMore()
        Mockito.verifyNoMoreInteractions(view)
        Mockito.verifyNoMoreInteractions(repo)
    }

    @Test
    fun loadMore_withSearchTerm() {
        vm.searchTerm = "Trees"
        vm.pageCount = 1
        vm.loadMore()
        Mockito.verify(view).displayState(ViewState.LOADING)
        Mockito.verify(repo).searchImages("Trees", 1, vm)
    }

    @Test
    fun onSuccess_ValidResponse() {
        vm.pageCount = 1
        val resp = getMockedResults()
        val viewData = getRecyclerViewData()
        Mockito.`when`(util.mapData(resp.photos.photo)).thenReturn(viewData)
        vm.onSuccess(resp)
        Assert.assertEquals(2, vm.pageCount)
        Mockito.verify(view).displayState(ViewState.COMPLETE)
        Mockito.verify(recyclerAdapterView).updateData(viewData, true, true)
    }

    @Test
    fun onSuccess_ErrorResponse() {
        vm.onSuccess(getEmptyMockedResults())
        Mockito.verify(view).displayState(ViewState.ERROR)
    }

    @Test
    fun onError_showError() {
        vm.pageCount = 1
        vm.onError(400, "Failed to load")
        Mockito.verify(view).displayState(ViewState.ERROR)
    }

    @Test
    fun onSearchComplete_sameOldQuery() {
        vm.searchTerm = "tree"
        vm.onSearchComplete("tree")
        Mockito.verifyNoMoreInteractions(view)
        Mockito.verifyNoMoreInteractions(repo)
    }

    @Test
    fun onSearchComplete_newQuery() {
        vm.searchTerm = "tree"
        vm.onSearchComplete("trees")
        Assert.assertEquals(1, vm.pageCount)
        Mockito.verify(repo).searchImages("trees", 1, vm)
    }


    private fun getMockedResults(): SearchResult {
        return Gson().fromJson(
            "{\"photos\":{\"page\":1,\"pages\":20458,\"perpage\":10,\"total\":\"204575\",\"photo\":[{\"id\":\"46323072861\",\"owner\":\"140559320@N08\",\"secret\":\"b9f2e97e04\",\"server\":\"4903\",\"farm\":5,\"title\":\"me\\ud83c\\udf32irl by Omwize MORE MEMES\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599768404\",\"owner\":\"108993666@N07\",\"secret\":\"449086dde8\",\"server\":\"4866\",\"farm\":5,\"title\":\"Place of Birth\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599746744\",\"owner\":\"143502056@N04\",\"secret\":\"60313519b1\",\"server\":\"4809\",\"farm\":5,\"title\":\"The Lake taken from Bow Bridge Central Park New York City NY P00048 DSC_2030\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"32451109958\",\"owner\":\"13922841@N00\",\"secret\":\"1a94bf4c09\",\"server\":\"4811\",\"farm\":5,\"title\":\"AGQ-20181202-0005\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45410564705\",\"owner\":\"147627672@N02\",\"secret\":\"3e32016da0\",\"server\":\"4815\",\"farm\":5,\"title\":\"reposted from reddit: Downtown Vancouver for a cozy Friday evening around the Christmas tree. (source in description)\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599055144\",\"owner\":\"55082908@N07\",\"secret\":\"a0300dd746\",\"server\":\"4821\",\"farm\":5,\"title\":\"20s approaching Thorpe Marsh Junction\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45104441694\",\"owner\":\"157619092@N06\",\"secret\":\"2277b2ca23\",\"server\":\"4850\",\"farm\":5,\"title\":\"Flam, Aurland, Norway\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"44506569930\",\"owner\":\"31513653@N03\",\"secret\":\"aeae5df91b\",\"server\":\"4844\",\"farm\":5,\"title\":\"\\u69cb\\u6210=Composition-161\\uff0fBetween small ones and big ones\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599740864\",\"owner\":\"95430950@N07\",\"secret\":\"fe0c56d355\",\"server\":\"4900\",\"farm\":5,\"title\":\"S05-87-018\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}",
            object : TypeToken<SearchResult>() {}.type
        )
    }

    private fun getEmptyMockedResults(): SearchResult {
        return Gson().fromJson(
            "{\"photos\":{\"page\":1,\"pages\":20458,\"perpage\":10,\"total\":\"204575\",\"photo\":[{\"id\":\"46323072861\",\"owner\":\"140559320@N08\",\"secret\":\"b9f2e97e04\",\"server\":\"4903\",\"farm\":5,\"title\":\"me\\ud83c\\udf32irl by Omwize MORE MEMES\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599768404\",\"owner\":\"108993666@N07\",\"secret\":\"449086dde8\",\"server\":\"4866\",\"farm\":5,\"title\":\"Place of Birth\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599746744\",\"owner\":\"143502056@N04\",\"secret\":\"60313519b1\",\"server\":\"4809\",\"farm\":5,\"title\":\"The Lake taken from Bow Bridge Central Park New York City NY P00048 DSC_2030\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"32451109958\",\"owner\":\"13922841@N00\",\"secret\":\"1a94bf4c09\",\"server\":\"4811\",\"farm\":5,\"title\":\"AGQ-20181202-0005\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45410564705\",\"owner\":\"147627672@N02\",\"secret\":\"3e32016da0\",\"server\":\"4815\",\"farm\":5,\"title\":\"reposted from reddit: Downtown Vancouver for a cozy Friday evening around the Christmas tree. (source in description)\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599055144\",\"owner\":\"55082908@N07\",\"secret\":\"a0300dd746\",\"server\":\"4821\",\"farm\":5,\"title\":\"20s approaching Thorpe Marsh Junction\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45104441694\",\"owner\":\"157619092@N06\",\"secret\":\"2277b2ca23\",\"server\":\"4850\",\"farm\":5,\"title\":\"Flam, Aurland, Norway\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"44506569930\",\"owner\":\"31513653@N03\",\"secret\":\"aeae5df91b\",\"server\":\"4844\",\"farm\":5,\"title\":\"\\u69cb\\u6210=Composition-161\\uff0fBetween small ones and big ones\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"45599740864\",\"owner\":\"95430950@N07\",\"secret\":\"fe0c56d355\",\"server\":\"4900\",\"farm\":5,\"title\":\"S05-87-018\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"error\"}",
            object : TypeToken<SearchResult>() {}.type
        )
    }

    private fun getRecyclerViewData(): List<ImageItemData> {
        return Gson().fromJson("[{\"title\":\"me\uD83C\uDF32irl by Omwize MORE MEMES\",\"imageUrl\":\"http://farm5.static.flickr.com/4903/46323072861_b9f2e97e04.jpg\"},{\"title\":\"Place of Birth\",\"imageUrl\":\"http://farm5.static.flickr.com/4866/45599768404_449086dde8.jpg\"},{\"title\":\"The Lake taken from Bow Bridge Central Park New York City NY P00048 DSC_2030\",\"imageUrl\":\"http://farm5.static.flickr.com/4809/45599746744_60313519b1.jpg\"},{\"title\":\"AGQ-20181202-0005\",\"imageUrl\":\"http://farm5.static.flickr.com/4811/32451109958_1a94bf4c09.jpg\"},{\"title\":\"reposted from reddit: Downtown Vancouver for a cozy Friday evening around the Christmas tree. (source in description)\",\"imageUrl\":\"http://farm5.static.flickr.com/4815/45410564705_3e32016da0.jpg\"},{\"title\":\"20s approaching Thorpe Marsh Junction\",\"imageUrl\":\"http://farm5.static.flickr.com/4821/45599055144_a0300dd746.jpg\"},{\"title\":\"Flam, Aurland, Norway\",\"imageUrl\":\"http://farm5.static.flickr.com/4850/45104441694_2277b2ca23.jpg\"},{\"title\":\"構成\\u003dComposition-161／Between small ones and big ones\",\"imageUrl\":\"http://farm5.static.flickr.com/4844/44506569930_aeae5df91b.jpg\"},{\"title\":\"S05-87-018\",\"imageUrl\":\"http://farm5.static.flickr.com/4900/45599740864_fe0c56d355.jpg\"}]", object : TypeToken<List<ImageItemData>>() {}.type)
    }
}