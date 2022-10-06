//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0DivPolyUV.java		�i�����Łj
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lv0DivPolyUV extends lvDivPolyUV {
	
	private static final int  maxNumVtxUV = 256;
		
// -------------------------------------------------------------------
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No			*/
		private int  curShellNo		= 0;
		/** �J�����gGS��No				*/
		private int  curGsNo		= 0;
		
		/** ��ʃ��C���[(topo0)�ɑ���f�[�^�i UV��Ԃ������Ȃ��ꍇ�́Anull�j					*/
		public  lvDivPolyUV.DownDivPolyUV  downDivPolyUV	= null;

		/** ��ʃ��C���[(topo0)���瑗����f�[�^�i UV��Ԃ������Ȃ��ꍇ�́Anull ���Ԃ�j		*/
		public  lvDivPolyUV.UpDivPolyUV    upDivPolyUV		= null;

		/** ���K�͂� DownDivPolyUV().uvSpace �p�̃O���[�o���f�[�^		*/
		private lvDivPolyUV.DownDivPolyUVone  tmpUVspace[]		= null;

		/** ���K�͂� DownDivPolyUV().uvSpace[].vtxUV �p�̃O���[�o���f�[�^		*/
		private lvUVdt                        tmpVtxUV[]		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			upDivPolyUV     = new lv0DivPolyUV.UpDivPolyUV();

			tmpUVspace      = new lv0DivPolyUV.DownDivPolyUVone[ 1 ];
			tmpUVspace[ 0 ] = new lv0DivPolyUV.DownDivPolyUVone();

			tmpVtxUV = new lvUVdt[ maxNumVtxUV ];
			for( int i=0; i<maxNumVtxUV; i++ )
				tmpVtxUV[ i ] = new lvUVdt();
		}
	}


	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGblElm )global.GAtt() ).gDivPolyUV;
	}
	/** DownDivPolyUV�p�̃O���[�o���f�[�^		*/
	private final lvDivPolyUV.DownDivPolyUV
	DownDivPolyUV()
	{
		return  Gbl().downDivPolyUV;
	}
	/** UpDivPolyUV�p�̃O���[�o���f�[�^			*/
	private final lvDivPolyUV.UpDivPolyUV
	UpDivPolyUV()
	{
		return Gbl().upDivPolyUV;
	}
	/** lvPolygon�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvUVspace�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvUVspace
	ShlUVspace()
	{
		if( ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell == null )
			return null;
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvSpace;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lv0DivPolyUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public void
	SetDownDivPolyUV( int shellNo, int gsNo, lvDivPolyUV.DownDivPolyUV downDivPolyUV0 ) throws lvThrowable
	{
		Gbl().curShellNo    = shellNo;
		Gbl().curGsNo       = gsNo;
		Gbl().downDivPolyUV = downDivPolyUV0;
		
		DownDivPolyUV().uvSpace = null;
		if( ShlUVspace() == null )
			return;

		lvRec.SeqPart  face = Polygon().face[ Gbl().curGsNo ];
		int  numUVspace = NewDownDivPolyUV( face.num );
		if( numUVspace == 0 )
			return;
	
		for( int i=0; i<numUVspace; i++ ) {
			for( int j=0; j<face.num; j++ ) {
				lvRec.SeqPart  gsUVinfo    = ShlUVspace().gsUV[ Gbl().curGsNo ];
				int            uvSpaceNoGs = ShlUVspace().gsUVspaceSeq[ gsUVinfo.start + i ].uvSpaceNo;
				
				lvPolygon.FaceHalf  faceHalfSeq = Polygon().faceHalfSeq[ face.start + j ];
				lvRec.SeqPart       vtxUVinfo   = ShlUVspace().vtxUV[ faceHalfSeq.vtxNo ];
				int  uvSpaceVtxOfs = GetUVspaceVtxOffset( uvSpaceNoGs, vtxUVinfo );
				
				lvUVdt  srcVtxUV = ShlUVspace().vtxUVinfoSeq[ vtxUVinfo.start + uvSpaceVtxOfs ].uv;
				lvUVdt  dstVtxUV = DownDivPolyUV().uvSpace[ i ].vtxUV[ j ];
				lvUVdt.Copy( srcVtxUV, dstVtxUV );
			}
		}
	}
	
	private final int
	NewDownDivPolyUV( int num )
	{
		int  numUVspace = ShlUVspace().gsUV[ Gbl().curGsNo ].num;
		if( numUVspace == 0 )
			return 0;
		
		if( numUVspace == 1 )
			DownDivPolyUV().uvSpace = Gbl().tmpUVspace;
		else {
			DownDivPolyUV().uvSpace = new lv0DivPolyUV.DownDivPolyUVone[ numUVspace ];
			DownDivPolyUV().uvSpace[ 0 ] = Gbl().tmpUVspace[ 0 ];
			for( int i=1; i<numUVspace; i++ )
				DownDivPolyUV().uvSpace[ i ] = new lv0DivPolyUV.DownDivPolyUVone();
		}
		
		for( int i=0; i<numUVspace; i++ )
			NewDownDivPolyUVmain( i, num );
			
		return numUVspace;
	}
	
	private final void
	NewDownDivPolyUVmain( int uvSpaceOffset, int num )
	{
		DownDivPolyUV().uvSpace[ uvSpaceOffset ].numVtxUV = num;
		
		if( uvSpaceOffset == 0 ) {
			if( num > maxNumVtxUV ) {
				DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV = new lvUVdt[ num ];
				for( int i=0; i<maxNumVtxUV; i++ )
					DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = Gbl().tmpVtxUV[ i ];
				for( int i=maxNumVtxUV; i<num; i++ )
					DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = new lvUVdt();
			}
			else
				DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV = Gbl().tmpVtxUV;
		}
		else {
			DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV = new lvUVdt[ num ];
			for( int i=0; i<num; i++ )
				DownDivPolyUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = new lvUVdt();
		}
	}
	
	private final int
	GetUVspaceVtxOffset( int uvSpaceNoGs, lvRec.SeqPart vtxUVinfo ) throws lvThrowable
	{
		int  uvSpaceVtxOfs;
		
		for( int i=0; i<vtxUVinfo.num; i++ ) {
			int  uvSpaceNoVtx = ShlUVspace().vtxUVinfoSeq[ vtxUVinfo.start + i ].uvSpaceNo;
			if( uvSpaceNoVtx == uvSpaceNoGs ) {
				uvSpaceVtxOfs = i;
				return uvSpaceVtxOfs;
			}
		}
		
		Err().Assert( false, "lvDivPolyUV.GetUVspaceVtxOffset(0)" );
		return 0;	// Dummy
	}

	public void
	SetUpDivPolyUV()
	{
		// no process
	}
	
	public void
	Finish()
	{
		DownDivPolyUV().uvSpace = null;		// Delete( DownDivPolyUV().uvSpace );
	}

}
