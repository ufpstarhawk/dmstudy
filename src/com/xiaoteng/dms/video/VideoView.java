package com.xiaoteng.dms.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.AudioEffect.OnControlStatusChangeListener;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController.MediaPlayerControl;

public class VideoView extends SurfaceView implements MediaPlayerControl{
	private String TAG = "ViedoView";
	//上下文
	private Context mContext;
	//视频路劲和持续时间
	private Uri mUri;
	private int mDuration;
	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayerControl mMediaPlayer = null;
	private boolean mIsPrepared;
	//视频宽高
	private int mVideoWidth;
	private int mVideoHight;
	//播放界面的宽高
	private int mSurfaceWidth;
	private int mSurfaceHight;
	//媒体控制器
	private MediaPlayerControl mMediaController;
	//播放完毕监听器
	private OnControlStatusChangeListener mOnCompletionListener;
	//播放准备监听器
	private MediaPlayer.OnPreparedListener mOnPreparedListener;
	private int mCurrentBufferPercentage;
	public VideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
