//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvError.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * エラー時処理のクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvError extends lvRoot {

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** lvDebugModeオブジェクト							*/
		private lvDebugMode  debug   		= null;
		
		/** lvConst.LV_FAILURE を返した場合のメッセージ		*/
		private String       message 		= null;
		
		/** throwモード										*/
		private int          throwModeCnt	= 0;

		// -------------------------------------------------------------------

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			debug = new lvDebugMode( dt );
		}

		// -------------------------------------------------------------------
		
		/** Throwモードを開始する		*/
		public final void
		BeginThrowMode()
		{
			throwModeCnt++;
		}
		/** Throwモードを終了する		*/
		public final void
		EndThrowMode()
		{
			if( throwModeCnt > 0 )
				throwModeCnt--;
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvError( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * もし chk が false なら、エラー時処理を行う
	 * @param  chk		(( I )) エラー判定式
	 * @param  s		(( I )) エラー時の出力文字列（デバッグモード時）
	 */
	public final void
	Assert( boolean chk, String s ) throws lvThrowable
	{
		if( chk == false ) {
			Gbl().debug.Println( s );
			
			if( Gbl().throwModeCnt > 0 ) {
				Gbl().message = new String( s );
				throw new lvThrowable();
			}
			else
				System.exit( 1 );
		}
	}
	
	/**
	 * ある関数が lvConst.LV_FAILURE を返した場合のメッセージを得る（ lvConst.LV_SUCCESS を返した場合、nullまたは過去のメッセージ）
	 * @param  gbl		(( I )) lvWorld.Init()で出力した lvGlobalデータ
	 * @return			メッセージ（ null の場合も有り）
	 */
	public static final String
	Message( lvGlobal gbl )
	{
		return  ( ( lvComGblElm )gbl.GCom() ).gError.message;
	}
}
