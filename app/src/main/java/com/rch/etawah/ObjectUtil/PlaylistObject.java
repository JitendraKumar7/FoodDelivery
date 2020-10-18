package com.rch.etawah.ObjectUtil;

public class PlaylistObject {
    private String id;
    private String playlistName;


    public String getId() {
        return id;
    }

    public PlaylistObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public PlaylistObject setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
        return this;
    }
}
