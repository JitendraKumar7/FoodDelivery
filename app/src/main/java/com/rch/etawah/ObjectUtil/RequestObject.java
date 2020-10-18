package com.rch.etawah.ObjectUtil;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.InterfaceUtil.InternetCallback;

public class RequestObject implements Parcelable {
    private Context context;
    private String serverUrl;
    private String requestType;
    private String json;
    private String loadingText;
    private String postId;
    private boolean share = false;
    private boolean isRead;
    private boolean firstRequest = true;
    private boolean isDownloadOnly = true;
    private Constant.CONNECTION_TYPE connectionType;
    private Constant.CONNECTION connection;
    private ConnectionCallback connectionCallback;
    private InternetCallback internetCallback;


    public boolean isDownloadOnly() {
        return isDownloadOnly;
    }

    public RequestObject setDownloadOnly(boolean downloadOnly) {
        isDownloadOnly = downloadOnly;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public RequestObject setContext(Context context) {
        this.context = context;
        return this;
    }

    public Constant.CONNECTION getConnection() {
        return connection;
    }

    public RequestObject setConnection(Constant.CONNECTION connection) {
        this.connection = connection;
        return this;
    }

    public boolean isRead() {
        return isRead;
    }

    public RequestObject setRead(boolean read) {
        isRead = read;
        return this;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public RequestObject setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public String getLoadingText() {
        return loadingText;
    }

    public RequestObject setLoadingText(String loadingText) {
        this.loadingText = loadingText;
        return this;
    }


    public String getRequestType() {
        return requestType;
    }

    public RequestObject setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public ConnectionCallback getConnectionCallback() {
        return connectionCallback;
    }

    public RequestObject setConnectionCallback(ConnectionCallback connectionCallback) {
        this.connectionCallback = connectionCallback;
        return this;
    }


    public boolean isShare() {
        return share;
    }

    public RequestObject setShare(boolean share) {
        this.share = share;
        return this;
    }


    public boolean isFirstRequest() {
        return firstRequest;
    }

    public RequestObject setFirstRequest(boolean firstRequest) {
        this.firstRequest = firstRequest;
        return this;
    }

    public Constant.CONNECTION_TYPE getConnectionType() {
        return connectionType;
    }

    public RequestObject setConnectionType(Constant.CONNECTION_TYPE connectionType) {
        this.connectionType = connectionType;
        return this;
    }


    public String getJson() {
        return json;
    }

    public RequestObject setJson(String json) {
        this.json = json;
        return this;
    }

    public RequestObject() {
    }

    public InternetCallback getInternetCallback() {
        return internetCallback;
    }

    public RequestObject setInternetCallback(InternetCallback internetCallback) {
        this.internetCallback = internetCallback;
        return this;
    }


    public String getPostId() {
        return postId;
    }

    public RequestObject setPostId(String postId) {
        this.postId = postId;
        return this;
    }


    @Override
    public String toString() {
        return "RequestObject{" +
                ", serverUrl='" + serverUrl + '\'' +
                ", requestType='" + requestType + '\'' +
                ", json='" + json + '\'' +
                ", loadingText='" + loadingText + '\'' +
                ", postId='" + postId + '\'' +
                ", share=" + share +
                ", isRead=" + isRead +
                ", firstRequest=" + firstRequest +
                ", isDownloadOnly=" + isDownloadOnly +
                ", connectionType=" + connectionType +
                ", connection=" + connection +
                ", connectionCallback=" + connectionCallback +
                ", internetCallback=" + internetCallback +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.serverUrl);
        dest.writeString(this.requestType);
        dest.writeString(this.json);
        dest.writeString(this.loadingText);
        dest.writeString(this.postId);
        dest.writeByte(this.share ? (byte) 1 : (byte) 0);
        dest.writeByte(this.firstRequest ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDownloadOnly ? (byte) 1 : (byte) 0);
        dest.writeInt(this.connectionType == null ? -1 : this.connectionType.ordinal());
        dest.writeInt(this.connection == null ? -1 : this.connection.ordinal());

    }

    protected RequestObject(Parcel in) {

        this.serverUrl = in.readString();
        this.requestType = in.readString();
        this.json = in.readString();
        this.loadingText = in.readString();
        this.postId = in.readString();
        this.share = in.readByte() != 0;
        this.firstRequest = in.readByte() != 0;
        this.isDownloadOnly = in.readByte() != 0;
        int tmpConnectionType = in.readInt();
        this.connectionType = tmpConnectionType == -1 ? null : Constant.CONNECTION_TYPE.values()[tmpConnectionType];
        int tmpConnection = in.readInt();
        this.connection = tmpConnection == -1 ? null : Constant.CONNECTION.values()[tmpConnection];
    }

    public static final Creator<RequestObject> CREATOR = new Creator<RequestObject>() {
        @Override
        public RequestObject createFromParcel(Parcel source) {
            return new RequestObject(source);
        }

        @Override
        public RequestObject[] newArray(int size) {
            return new RequestObject[size];
        }
    };
}
