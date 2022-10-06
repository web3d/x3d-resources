//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVpubCorrect.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/10-)
 * 
 */
public class lvMakeUVpubCorrect extends lvRoot {

	/** �o�̓f�[�^�p		*/
	public static class DstInfo {
		
		/** ���E�Z�O�����g�� 0�{�̏ꍇ�ɋ��E�Ƃ��� GS��No.		*/
		public int      selGsNoForNonBnd;

		/** �u���̓���UV��Ԃ͋��E�Z�O�����g�������H�v�̔z��i����UV��Ԃ̐��������݂���j		*/
		public boolean  hasBound[]			= null;
	}
	
// -------------------------------------------------------------------

	/**
	 * ���E�Z�O�����g�ɑ΂���ꎞ�I�����\����
	 */
	private static class TmpHalf {

		/** ���E�Z�O�����g��ɂ��邩�H									*/
		private boolean  onBound;

		/** �ꎞ�I���E�Z�O�����g�����p���񃊃X�g�i�Е����j				*/
		private int      nextGsNo;
		private int      nextHalfNo;
		
		/** SetUVnumBnd(), SetUVbndSeq() �p�̈ꎞ�t���O					*/
		private boolean  alreadyUsed;
	}
	
// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No						*/
		private int                         curShellNo;

		/** ���̓f�[�^								*/
		private lvMakeUVspaceType.UVpublic  srcUVpublic			= null;
		
		/** ���̓f�[�^								*/
		private lvUVpublic                  srcShlUVpublic		= null;
		
		/** ���E�Z�O�����g�ɑ΂���ꎞ���i�z�񒷂́APolygon().face[ ngStartNo ].start �ɓ����� �j	*/
		private TmpHalf                     tmpHalf[]			= null;

		/** �u����GS�ʂ́A�Y��UV���No.�������H�v�̔z��iGS�ʂ̐��������݂���j					*/
		private boolean                     gsHasUVspace[]		= null;
		
		/** �o�̓f�[�^�p		*/
		private DstInfo                     dstInfo				= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}
		
		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
		private lvVector  tvSelFaceForNonBnd[]	= null;
		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvSelFaceForNonBnd = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSelFaceForNonBnd[ i ] = new lvVector( dt );
		}
	}


	/** ���N���X�p�̃O���[�o���f�[�^							*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVpubCorrect;
	}
	/** lvMakeUVspaceType.UVpublic�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvMakeUVspaceType.UVpublic
	SrcUVpublic()
	{
		return  Gbl().srcUVpublic;
	}
	/** lvUVpublic�f�[�^�p�N���X�I�u�W�F�N�g					*/
	private final lvUVpublic
	SrcShlUVpublic()
	{
		return  Gbl().srcShlUVpublic;
	}
	/** lvMakeUVpubCorrect.DstInfo�f�[�^�p�N���X�I�u�W�F�N�g	*/
	private final lvMakeUVpubCorrect.DstInfo
	DstInfo()
	{
		return  Gbl().dstInfo;
	}
	/** lvPolygon�f�[�^�p�N���X�I�u�W�F�N�g						*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lvMakeUVpubCorrect( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public final void
	Exec( int shellNo, lvMakeUVspaceType.UVpublic srcUVpublic, lvUVpublic srcShlUVpublic,
			lvMakeUVpubCorrect.DstInfo dstInfo ) throws lvThrowable
	{
		if( srcUVpublic == null )
			return;
			
		Gbl().curShellNo     = shellNo;
		Gbl().srcUVpublic    = srcUVpublic;
		Gbl().srcShlUVpublic = srcShlUVpublic;
		Gbl().dstInfo        = dstInfo;

		ExecMain();
		Finish();
	}

	private final void
	ExecMain() throws lvThrowable
	{
		int  numUVspace = SrcShlUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			ExecOne( i );
			SetDstUVpublic( i );
		}
	}
	
	private final void
	ExecOne( int uvSpaceNo ) throws lvThrowable
	{
		if( uvSpaceNo == 0 ) {
			NewTmpHalf();
			NewGsHasUVspace();
		}
		
		InitBoundList( uvSpaceNo );
		ChkOnBound();
		JoinBoundList();
	}
	
	private final void
	NewTmpHalf()
	{
		int  numHalf;
		if( Polygon().face.length > Polygon().ngStartNo )
			numHalf = Polygon().face[ Polygon().ngStartNo ].start;
		else
			numHalf = Polygon().faceHalfSeq.length;
		Gbl().tmpHalf = new TmpHalf[ numHalf ];
		for( int i=0; i<numHalf; i++ )
			Gbl().tmpHalf[ i ] = new TmpHalf();
	}

	private final void
	NewGsHasUVspace()
	{
		int  numGs = Polygon().ngStartNo;
		Gbl().gsHasUVspace = new boolean[ numGs ];
	}

	private final void
	InitBoundList( int uvSpaceNo )
	{
		int  numGs = Polygon().ngStartNo;
		
		for( int i=0; i<numGs; i++ ) {
			Gbl().gsHasUVspace[ i ] = false;
			
			lvRec.SeqPart  gsNumUV = SrcUVpublic().gsNumUV[ i ];
			boolean  exist = false;
			for( int j=0; j<gsNumUV.num; j++ ) {
				if( SrcUVpublic().gsUVseq[ gsNumUV.start + j ] == uvSpaceNo ) {
					exist = true;
					break;
				}
			}
			if( exist == true )
				Gbl().gsHasUVspace[ i ] = true;
		}
		
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				Gbl().tmpHalf[ face.start + j ].onBound  = false;
				Gbl().tmpHalf[ face.start + j ].nextGsNo = -1;
			}
		}
	}
	
	private final void
	ChkOnBound()
	{
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  edgeNo  = Polygon().faceHalfSeq[ face.start + j ].edgeNo;
				int  edgeIdx = Polygon().faceHalfSeq[ face.start + j ].edgeIdx;
				
				boolean  isSame = IsSameUVspaceFace( edgeNo, 1-edgeIdx );
				if( isSame == true )
					continue;
				
				Gbl().tmpHalf[ face.start + j ].onBound = true;
			}
		}
	}

	private final boolean
	IsSameUVspaceFace( int edgeNo, int edgeIdx )
	{
		lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ edgeNo ].face[ edgeIdx ];
		if( faceHalf.faceNo >= Polygon().ngStartNo )
			return false;
		
		if( Gbl().gsHasUVspace[ faceHalf.faceNo ] == false )
			return false;
			
		return true;
	}
	
	private final void
	JoinBoundList() throws lvThrowable
	{
		int  num = Polygon().vertex.length;
		for( int i=0; i<num; i++ )
			JoinBoundListMain( i );
	}
	
	private final void
	JoinBoundListMain( int vtxNo ) throws lvThrowable
	{
		int  startNoOrg = -1;
		int  gsNoOrg    = -1;
		int  halfNoOrg  = -1;
		int  startNo, num, nextStartNo, gsNo, halfNo;
		
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		
		boolean  init = true;
		int      cnt  = 0;
		for( int i=0; i<vtxFace.num; i++ ) {
			lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + i ];
			lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
			if( Gbl().tmpHalf[ face.start + vtxFaceSeq.halfNo ].onBound == true ) {
				if( init == true ) {
					init = false;
					startNoOrg = i;
					gsNoOrg    = vtxFaceSeq.faceNo;
					halfNoOrg  = vtxFaceSeq.halfNo;
				}
				cnt++;
			}
		}
		if( cnt == 0 )
			return;
		
		startNo = startNoOrg;
		for( int i=0; i<cnt; i++ ) {
			num         = -1;
			gsNo        = -1;
			halfNo      = -1;
			nextStartNo = -1;
			if( i < ( cnt-1 ) ) {
				boolean  chk = false;
				for( int j=( startNo+1 ); j<vtxFace.num; j++ ) {
					lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
					lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
					if( Gbl().tmpHalf[ face.start + vtxFaceSeq.halfNo ].onBound == true ) {
						chk    = true;
						nextStartNo = j;
						num    = nextStartNo - startNo;
						gsNo   = vtxFaceSeq.faceNo;
						halfNo = vtxFaceSeq.halfNo;
						break;
					}
				}
				Err().Assert( ( chk == true ), "lvMakeUVpubCorrect.JoinBoundListMain(0)" );
			}
			else {
				num    = ( startNoOrg + vtxFace.num ) - startNo;
				gsNo   = gsNoOrg;
				halfNo = halfNoOrg;
			}
			
			JoinBoundListOne( vtxNo, startNo, num, gsNo, halfNo );
			if( i < ( cnt-1 ) )
				startNo = nextStartNo;
		}
	}

	private final void
	JoinBoundListOne( int vtxNo, int startNo, int num, int gsNo, int halfNo ) throws lvThrowable
	{
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		
		boolean  chk = false;
		for( int i=0; i<num; i++ ) {
			int  j = ( startNo + i ) % vtxFace.num;
			lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
			lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
			int  prevHalfNo = ( vtxFaceSeq.halfNo + face.num - 1 ) % face.num;
			if( Gbl().tmpHalf[ face.start + prevHalfNo ].onBound == true ) {
				chk = true;
				Gbl().tmpHalf[ face.start + prevHalfNo ].nextGsNo   = gsNo;
				Gbl().tmpHalf[ face.start + prevHalfNo ].nextHalfNo = halfNo;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVpubCorrect.JoinBoundListOne(0)" );
	}
	
	private final void
	SetDstUVpublic( int uvSpaceNo ) throws lvThrowable
	{
		if( uvSpaceNo == 0 )
			SelGsNoForNonBnd();
			
		SetHasBound( uvSpaceNo );
	}
	
	private final void
	SelGsNoForNonBnd() throws lvThrowable
	{
		lvVector  center = Gbl().tvSelFaceForNonBnd[ 0 ];			// center = new lvVector( global );
		lvVector  pos    = Gbl().tvSelFaceForNonBnd[ 1 ];			// pos    = new lvVector( global );
		
		double  min     =  0.0;
		int     minGsNo = -1;
		
		int  numGS = Polygon().ngStartNo;
		for( int i=0; i<numGS; i++ ) {
			center.SetXYZ( 0.0, 0.0, 0.0 );
			lvRec.SeqPart  face = Polygon().face[ i ];
			
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo = Polygon().faceHalfSeq[ face.start + j ].vtxNo;
				pos.VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
				center.AddAssign( pos );
			}
			center.DivAssign( face.num );
			
			double  len = center.Length2();
			if( i == 0 || min > len ) {
				min     = len;
				minGsNo = i;
			}
		}
		
		DstInfo().selGsNoForNonBnd = minGsNo;
	}
	
	private final void
	SetHasBound( int uvSpaceNo ) throws lvThrowable
	{
		if( uvSpaceNo == 0 )
			NewHasBound();
		
		SetHasBound0( uvSpaceNo );
	}
	
	private final void
	NewHasBound()
	{
		int  numUVspace = SrcShlUVpublic().uvSpace.length;
		DstInfo().hasBound = new boolean[ numUVspace ];
	}
	
	private final void
	SetHasBound0( int uvSpaceNo ) throws lvThrowable
	{
		InitAlreadyUsed();
		SetHasBound0Main( uvSpaceNo );
	}
	
	private final void
	InitAlreadyUsed()
	{
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ )
				Gbl().tmpHalf[ face.start + j ].alreadyUsed = false;
		}
	}
	
	private final void
	SetHasBound0Main( int uvSpaceNo ) throws lvThrowable
	{
		int  numUVnumBnd = 0;
		
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				if( Gbl().tmpHalf[ face.start + j ].onBound     == true  &&
					Gbl().tmpHalf[ face.start + j ].alreadyUsed == false )
				{
					SetHasBound0One( i, j );
					numUVnumBnd++;
				}
			}
		}
		
		DstInfo().hasBound[ uvSpaceNo ] = true;
		if( numUVnumBnd == 0 )
			DstInfo().hasBound[ uvSpaceNo ] = false;
	}
	
	private final void
	SetHasBound0One( int gsNo0, int halfNo0 ) throws lvThrowable
	{
		int  numHalf = Gbl().tmpHalf.length;
		
		int      gsNo   = gsNo0;
		int      halfNo = halfNo0;
		boolean  chk    = false;
		for( int i=0; i<numHalf; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ gsNo ];
			Gbl().tmpHalf[ face.start + halfNo ].alreadyUsed = true;
			gsNo = Gbl().tmpHalf[ face.start + halfNo ].nextGsNo;
			Err().Assert( ( gsNo >= 0 ), "lvMakeUVpubCorrect.SetHasBound0One(0)" );
			
			halfNo = Gbl().tmpHalf[ face.start + halfNo ].nextHalfNo;
			if( gsNo == gsNo0 && halfNo == halfNo0 ) {
				chk = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVpubCorrect.SetHasBound0One(1)" );
	}

	private final void
	Finish()
	{
		Gbl().srcUVpublic    = null;
		Gbl().srcShlUVpublic = null;
		Gbl().tmpHalf        = null;
		Gbl().gsHasUVspace   = null;
	}

}
