package com.robinsingh.example.android.multiscreen;

/*
 * Represents a vocabulary that Words that user wants to learn
 * It contains English translation and MiWok translation of that word
 * */
public class Words {
    //Default  translation of the word
    private String mDefaultTranslation;
    //MiWok translation of the word
    private String mMiWokTranslation;
    //Icon of the words
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioResourceId;

    //Takes the defaultTranslation and miWokTranslation as parameter
    public Words(String deafultTranslation, String miWokTranslation, int audioResourceId) {
        mDefaultTranslation = deafultTranslation;
        mMiWokTranslation = miWokTranslation;
        mAudioResourceId = audioResourceId;
    }

    //Takes the defaultTranslation, miWokTranslation and imageId as parameter
    public Words(String deafultTranslation, String miWokTranslation, int imageId, int audioResourceId) {
        mDefaultTranslation = deafultTranslation;
        mMiWokTranslation = miWokTranslation;
        mImageResourceId = imageId;
        mAudioResourceId = audioResourceId;
    }

    //Get  default translation of the word
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    //Get MiWok translation of the word
    public String getmMiWokTranslation() {
        return mMiWokTranslation;
    }

    //Get the image resource id
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    //Checks whether the listView itm has image id or not and returns boolean value
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //get Audio file id
    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}
