//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0DivFaceUV.java		�i�����Łj
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.tex.a0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lv0DivFaceUV extends lvDivFaceUV {
	
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
		public  lvDivFaceUVtype.DownDivFaceUV     downDivFaceUV		= null;

		/** ��ʃ��C���[(topo0)���瑗����f�[�^�i UV��Ԃ������Ȃ��ꍇ�́Anull ���Ԃ�j		*/
		public lvDivFaceUVtype.UpDivFaceUV        upDivFaceUV		= null;

		/** ���K�͂� DownDivFaceUV().uvSpace �p�̃O���[�o���f�[�^		*/
		private lvDivFaceUVtype.DownDivFaceUVone  tmpUVspace[]		= null;

		/** ���K�͂� DownDivFaceUV().uvSpace[].vtxUV �p�̃O���[�o���f�[�^		*/
		private lvUVdt  tmpVtxUV[]		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			upDivFaceUV     = new lvDivFaceUVtype.UpDivFaceUV();

			tmpUVspace      = new lvDivFaceUVtype.DownDivFaceUVone[ 1 ];
			tmpUVspace[ 0 ] = new lvDivFaceUVtype.DownDivFaceUVone();

			tmpVtxUV = new lvUVdt[ maxNumVtxUV ];
			for( int i=0; i<maxNumVtxUV; i++ )
				tmpVtxUV[ i ] = new lvUVdt();
		}
	}


	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGGblElm )global.GAttG() ).gDivFaceUV;
	}
	/** DownDivFaceUV�p�̃O���[�o���f�[�^		*/
	private final lvDivFaceUVtype.DownDivFaceUV
	DownDivFaceUV()
	{
		return  Gbl().downDivFaceUV;
	}
	/** UpDivFaceUV�p�̃O���[�o���f�[�^			*/
	private final lvDivFaceUVtype.UpDivFaceUV
	UpDivFaceUV()
	{
		return  Gbl().upDivFaceUV;
	}
	/** lvFacegon�f�[�^�p�N���X�I�u�W�F�N�g		*/
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
	public  lv0DivFaceUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public void
	SetDownDivFaceUV( int shellNo, int gsNo, lvDivFaceUVtype.DownDivFaceUV downDivFaceUV0 ) throws lvThrowable
	{
		Gbl().curShellNo    = shellNo;
		Gbl().curGsNo       = gsNo;
		Gbl().downDivFaceUV = downDivFaceUV0;
		
		DownDivFaceUV().uvSpace = null;
		if( ShlUVspace() == null )
			return;

		lvRec.SeqPart  face = Polygon().face[ Gbl().curGsNo ];
		int  numUVspace = NewDownDivFaceUV( face.num );
		if( numUVspace == 0 )
			return;
	
		lvRec.SeqPart  gsUVinfo = ShlUVspace().gsUV[ Gbl().curGsNo ];
		for( int i=0; i<numUVspace; i++ ) {
			int  uvSpaceNoGs = ShlUVspace().gsUVspaceSeq[ gsUVinfo.start + i ].uvSpaceNo;
				
			lvUVdt  srcCenter = ShlUVspace().gsUVspaceSeq[ gsUVinfo.start + i ].center;
			lvUVdt  dstCenter = DownDivFaceUV().uvSpace[ i ].center;
			lvUVdt.Copy( srcCenter, dstCenter );
			
			for( int j=0; j<face.num; j++ ) {
				lvPolygon.FaceHalf  faceHalfSeq = Polygon().faceHalfSeq[ face.start + j ];
				lvRec.SeqPart       vtxUVinfo   = ShlUVspace().vtxUV[ faceHalfSeq.vtxNo ];
				int  uvSpaceVtxOfs = GetUVspaceVtxOffset( uvSpaceNoGs, vtxUVinfo );
				
				lvUVdt  srcVtxUV = ShlUVspace().vtxUVinfoSeq[ vtxUVinfo.start + uvSpaceVtxOfs ].uv;
				lvUVdt  dstVtxUV = DownDivFaceUV().uvSpace[ i ].vtxUV[ j ];
				lvUVdt.Copy( srcVtxUV, dstVtxUV );
			}
		}
	}
	
	private final int
	NewDownDivFaceUV( int num )
	{
		int  numUVspace = ShlUVspace().gsUV[ Gbl().curGsNo ].num;
		if( numUVspace == 0 )
			return 0;
		
		if( numUVspace == 1 )
			DownDivFaceUV().uvSpace = Gbl().tmpUVspace;
		else {
			DownDivFaceUV().uvSpace = new lvDivFaceUVtype.DownDivFaceUVone[ numUVspace ];
			DownDivFaceUV().uvSpace[ 0 ] = Gbl().tmpUVspace[ 0 ];
			for( int i=1; i<numUVspace; i++ )
				DownDivFaceUV().uvSpace[ i ] = new lvDivFaceUVtype.DownDivFaceUVone();
		}
		
		for( int i=0; i<numUVspace; i++ )
			NewDownDivFaceUVmain( i, num );
			
		return numUVspace;
	}
	
	private final void
	NewDownDivFaceUVmain( int uvSpaceOffset, int num )
	{
		DownDivFaceUV().uvSpace[ uvSpaceOffset ].numVtxUV = num;
		
		if( uvSpaceOffset == 0 ) {
			if( num > maxNumVtxUV ) {
				DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV = new lvUVdt[ num ];
				for( int i=0; i<maxNumVtxUV; i++ )
					DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = Gbl().tmpVtxUV[ i ];
				for( int i=maxNumVtxUV; i<num; i++ )
					DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = new lvUVdt();
			}
			else
				DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV = Gbl().tmpVtxUV;
		}
		else {
			DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV = new lvUVdt[ num ];
			for( int i=0; i<num; i++ )
				DownDivFaceUV().uvSpace[ uvSpaceOffset ].vtxUV[ i ] = new lvUVdt();
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
		
		Err().Assert( false, "lvDivFaceUV.GetUVspaceVtxOffset(0)" );
		return 0;	// Dummy
	}

	public void
	SetUpDivFaceUV()
	{
		// no process
	}
	
	public void
	Finish()
	{
		DownDivFaceUV().uvSpace = null;		// Delete( DownDivFaceUV().uvSpace );
	}

}
