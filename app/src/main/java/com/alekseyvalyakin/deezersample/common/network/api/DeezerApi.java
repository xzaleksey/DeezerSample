package com.alekseyvalyakin.deezersample.common.network.api;

import com.alekseyvalyakin.deezersample.common.network.response.AlbumResponse;
import com.alekseyvalyakin.deezersample.common.network.response.AlbumsListResponse;
import com.alekseyvalyakin.deezersample.common.network.response.ArtistListResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeezerApi {

    @GET(ApiParams.PATH_SEARCH_ARTIST)
    Single<ArtistListResponse> searchArtist(@Query(ApiParams.PARAM_QUERY) String query);


    @GET(ApiParams.PATH_ARTIST_ALBUM)
    Single<AlbumsListResponse> getArtistAlbums(@Path(ApiParams.PARAM_ID) String artistId);

    @GET(ApiParams.PATH_ALBUM)
    Single<AlbumResponse> getAlbum(@Path(ApiParams.PARAM_ID) String albumId);
}
