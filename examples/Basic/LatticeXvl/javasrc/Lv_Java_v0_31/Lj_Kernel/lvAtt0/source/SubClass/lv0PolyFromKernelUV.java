//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0PolyFromKernelUV.java		�i�����Łj
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lv0PolyFromKernelUV extends lvPolyFromKernelUV {

	private static class DstUVspace {
		
		/**
		 * UV��ԏ��i�e�N�X�`���p�j�B���V�F����UV��ԁi�e�N�X�`���j�������Ȃ����́Anull �ƂȂ�B	<br>
		 * ���d�}�b�s���O�̏ꍇ�́A�z�񒷂� 2 �ȏ�ƂȂ� --- �����l null
		 */
		public lvFromKernelUV  uvSpace[]	= null;
	}

// -------------------------------------------------------------------

	/**
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No			*/
		private int  curShellNo		= 0;
		/** �J�����gGS��No				*/
		private int  curGsNo		= 0;
		
		/** ��ʃ��C���[(base0)���瑗����f�[�^�i UV��Ԃ������Ȃ��ꍇ�́Anull ���Ԃ�j	*/
		public  lvDivPolyUV.UpDivPolyUV  upDivPolyUV	= null;

		/** ��ʃ��C���[(base0)�ɑ���f�[�^�i UV��Ԃ������Ȃ��ꍇ�́Anull�j				*/
		private DstUVspace               dstUVspace		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGblElm )global.GAtt() ).gPolyFromKernelUV;
	}
	/** UpDivPolyUV�p�̃O���[�o���f�[�^				*/
	private final lvDivPolyUV.UpDivPolyUV
	UpDivPolyUV()
	{
		return  Gbl().upDivPolyUV;
	}
	/** UpDivUVspace�p�̃O���[�o���f�[�^				*/
	private final DstUVspace
	DstUVspace()
	{
		return  Gbl().dstUVspace;
	}
	/** lvUVspace�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvUVspace
	ShlUVspace()
	{
		if( ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell == null )
			return null;
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvSpace;
	}
	/** lvUVpublic�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvUVpublic
	ShlUVpublic()
	{
		if( ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell == null )
			return null;
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvPublic;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lv0PolyFromKernelUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public lvFromKernelUV[]
	ExecPoly( int shellNo, int gsNo, lvDivPolyUV.UpDivPolyUV upDivPolyUV ) throws lvThrowable
	{
		DstUVspace  dstUVspace = new DstUVspace();
		
		NewUpDivUVspace( shellNo, gsNo, dstUVspace );
		ExecUVspaceNo( shellNo, gsNo, dstUVspace );
		ExecPolyProc( upDivPolyUV, dstUVspace );
		
		Finish();
		
		return dstUVspace.uvSpace;
	}

	private final void
	NewUpDivUVspace( int shellNo, int gsNo, DstUVspace dstUVspace )
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		Gbl().dstUVspace = dstUVspace;
		
		DstUVspace().uvSpace = null;
		if( ShlUVspace() == null )
			return;

		NewUpDivUVspaceMain();
	}
	
	private final void
	NewUpDivUVspaceMain()
	{
		int  num = ShlUVspace().gsUV[ Gbl().curGsNo ].num;
		if( num == 0 )
			return;
		
		DstUVspace().uvSpace = new lvFromKernelUV[ num ];
		for( int i=0; i<num; i++ )
			DstUVspace().uvSpace[ i ] = new lvFromKernelUV();
	}
	
	private final void
	ExecUVspaceNo( int shellNo, int gsNo, DstUVspace dstUVspace )
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		Gbl().dstUVspace = dstUVspace;
		
		if( DstUVspace().uvSpace == null )
			return;

		ExecUVspaceNoMain();
	}

	private final void
	ExecUVspaceNoMain()
	{
		lvRec.SeqPart  gsUV = ShlUVspace().gsUV[ Gbl().curGsNo ];
		
		for( int i=0; i<gsUV.num; i++ ) {
			int  uvSpaceNo = ShlUVspace().gsUVspaceSeq[ gsUV.start + i ].uvSpaceNo;
			DstUVspace().uvSpace[ i ].uvSpaceNo = ShlUVpublic().uvSpace[ uvSpaceNo ].uvPublicNo;
		}
	}
	
	private final void
	ExecPolyProc( lvDivPolyUV.UpDivPolyUV upDivPolyUV, DstUVspace dstUVspace ) throws lvThrowable
	{
		Gbl().upDivPolyUV = upDivPolyUV;
		Gbl().dstUVspace  = dstUVspace;

		if( DstUVspace().uvSpace == null )
			return;

		ExecPolyMain();
	}

	private final void
	ExecPolyMain() throws lvThrowable
	{
		int  numUVspace = DstUVspace().uvSpace.length;
		Err().Assert( ( numUVspace == UpDivPolyUV().uvSpace.length ), "lvDivUVspace.ExecPoly(0)" );
		
		for( int i=0; i<numUVspace; i++ ) {
			int  numVtx = UpDivPolyUV().uvSpace[ i ].numVertex;
			NewUV( i, numVtx );
			for( int j=0; j<numVtx; j++ ) {
				lvUVdt  srcUV = UpDivPolyUV().uvSpace[ i ].vertex[ j ];
				lvUVdt  dstUV = DstUVspace().uvSpace[ i ].uv[ j ];
				lvUVdt.Copy( srcUV, dstUV );
			}
		}
	}
		
	private final void
	NewUV( int uvSpaceNo, int numVtx )
	{
		DstUVspace().uvSpace[ uvSpaceNo ].uv = new lvUVdt[ numVtx ];
		for( int i=0; i<numVtx; i++ )
			DstUVspace().uvSpace[ uvSpaceNo ].uv[ i ] = new lvUVdt();
	}

	private final void
	Finish()
	{
		Gbl().upDivPolyUV = null;
	}
	
}
