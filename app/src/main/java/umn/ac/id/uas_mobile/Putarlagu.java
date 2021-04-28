package umn.ac.id.uas_mobile;

import android.net.Uri;

public class Putarlagu {

    String judulaud;
    String countduration;
    String artist;
    Uri audUri;

    public String getjudulaud() {
        return judulaud;
    }

    public void setjudulaud(String judulaud) {
        this.judulaud = judulaud;
    }

    public String getcountduration() {
        return countduration;
    }

    public void setcountduration(String countduration) {
        this.countduration = countduration;
    }

    public String getartist() {
        return artist;
    }

    public void setartist(String artist) {
        this.artist = artist;
    }

    public Uri getaudUri() {
        return audUri;
    }

    public void setaudUri(Uri audUri) {
        this.audUri = audUri;
    }

}