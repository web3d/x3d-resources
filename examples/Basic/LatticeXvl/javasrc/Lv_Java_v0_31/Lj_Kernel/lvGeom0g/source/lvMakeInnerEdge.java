//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeInnerEdge.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * 1�̖ʂɑ��݂��邷�ׂĂ̓����Ő����쐬����N���X
 * @author	  created by Eishin Matsui (00/06/14-)
 * 
 */
public class lvMakeInnerEdge extends lvRoot {

	private static final int  maxNumInner      = 256;
		
	private static final int  maxNumCross	   = 256;
	private static final int  maxNumStdEdge    = 256;
	
// -------------------------------------------------------------------
	
	/**
	 * �n�[�t�G�b�W���_�̏��̈ꎞ�I�����N���X
	 */
	private static class TmpCross {
		
		/** �n�[�t�G�b�W���_�̈ʒu			*/
		private lvVector  pos			= null;
		
		/** �n�[�t�G�b�W���_���牄�т�2����CBD( b�x�N�g�� �j		*/
		private lvVector  bCbdDim2		= null;
	
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public TmpCross( lvGlobal dt )
		{
			pos      = new lvVector( dt );
			bCbdDim2 = new lvVector( dt );
		}
	}
	
	/**
	 * Gbl().curDeriveDivFace���̍쐬�̍ہA�ꎞ�I�Ɏg�p������̂��߂̓����N���X
	 */
	private static class TmpMakeInnerEdge {
		
		/** �ꎞ�I�Ɏg�p��������Ő��Ɋւ�����			*/
		private TmpCross  cross[]				= null;
		
	}
	
// -------------------------------------------------------------------
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�́u��ʃ��C���[(topo0)���瑗����f�[�^�v		*/
		private lvDivFaceType.DownDivFace    curDownDivFace		= null;
		/** �J�����g�́uDownDivFace����h�������f�[�^�v				*/
		private lvDivFaceType.DeriveDivFace  curDeriveDivFace	= null;

		/** �V�F���쐬�̍ہA�ꎞ�I�Ɏg�p������		*/
		private TmpMakeInnerEdge  tmpMakeInnerEdge				= null;

		private lvMakeCbd         makeCbd						= null;
		
		private lvVector          antiCenter					= null;
		private lvVector          antiVtx0						= null;
		private lvVector          antiVtx1						= null;

		// -------------------------------------------------------------------

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public	Global( lvGlobal dt )
		{
			curDownDivFace   = new lvDivFaceType.DownDivFace( dt );
			curDeriveDivFace = new lvDivFaceType.DeriveDivFace( dt );
			tmpMakeInnerEdge = new TmpMakeInnerEdge();
			
			makeCbd          = new lvMakeCbd( dt );
			
			antiCenter       = new lvVector( dt );
			antiVtx0         = new lvVector( dt );
			antiVtx1         = new lvVector( dt );

			GlobalTmp( dt );
			GlobalStatic( dt );
		}

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
		private lvMakeCbd.DownCbd    tdExecInner0[]				= null;
		private lvMakeCbd.UpHalfCbd  tuExecInner0[]				= null;
		private lvVector             tvExecInner0Main[]			= null;
		private lvVector             tvAryDerivative[/*4*/]		= null;
		private lvVector             tvExecCenterPos0[]			= null;
		private lvVector             tvExecCenterPos1[]			= null;
		private lvVector             tvExecCenterNormal[]		= null;
		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tdExecInner0 = new lvMakeCbd.DownCbd[ 2 ];   for( int i=0; i<2; i++ ) tdExecInner0[ i ] = new lvMakeCbd.DownCbd( dt );
			tuExecInner0 = new lvMakeCbd.UpHalfCbd[ 2 ]; for( int i=0; i<2; i++ ) tuExecInner0[ i ] = new lvMakeCbd.UpHalfCbd( dt );
			
			tvExecInner0Main   = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvExecInner0Main[ i ]   = new lvVector( dt );
			tvAryDerivative    = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryDerivative[ i ]    = new lvVector( dt );
			tvExecCenterPos0   = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvExecCenterPos0[ i ]   = new lvVector( dt );
			tvExecCenterPos1   = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvExecCenterPos1[ i ]   = new lvVector( dt );
			tvExecCenterNormal = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvExecCenterNormal[ i ] = new lvVector( dt );
		}

		/** ���K�͂� DeriveDivFace0().inner �p�̃O���[�o���f�[�^		*/
		private lvDivFaceType.DeriveInner    staticInner[]		= null;
		
		/** ���K�͂Ȕz��p�̃O���[�o���f�[�^				*/
		private TmpCross  svCross[]								= null;
		private lvVector  svStdEdge[]							= null;
		
		/**
		 * ���K�͂Ȕz��p�̃O���[�o���f�[�^�̏������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		private final void
		GlobalStatic( lvGlobal dt )
		{
			staticInner = new lvDivFaceType.DeriveInner[ maxNumInner ];
			for( int i=0; i<maxNumInner; i++ )
				staticInner[ i ]  = new lvDivFaceType.DeriveInner( dt );

			svCross = new TmpCross[ maxNumCross ];
			for( int i=0; i<maxNumCross; i++ )
				svCross[ i ] = new TmpCross( dt );

			svStdEdge = new lvVector[ maxNumStdEdge ];
			for( int i=0; i<maxNumStdEdge; i++ )
				svStdEdge[ i ] = new lvVector( dt );
		}

	}

// -------------------------------------------------------------------

	/** ���N���X�p�̃O���[�o���f�[�^				*/
	private final Global
	Gbl()
	{
		return	( ( lv0GeomGGblElm )global.GGeomG() ).gMakeInnerEdge;
	}
	/** DownDivFace�p�̃O���[�o���f�[�^				*/
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return	Gbl().curDownDivFace;
	}
	/** DeriveDivFace�p�̃O���[�o���f�[�^			*/
	private final lvDivFaceType.DeriveDivFace
	DeriveDivFace()
	{
		return	Gbl().curDeriveDivFace;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public	lvMakeInnerEdge( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	/**
	 * ���s�֐�
	 */
	public final void
	Exec( lvDivFaceType.DownDivFace downDivFace, lvDivFaceType.DeriveDivFace deriveDivFace ) throws lvThrowable
	{
		if( downDivFace.numHalf == 4 )
			return;
		
		Gbl().curDownDivFace   = downDivFace;
		Gbl().curDeriveDivFace = deriveDivFace;
		
		NewDeriveDivFace();
		
		ExecMain();
		Finish();
	}

	private final void
	NewDeriveDivFace()
	{
		NewDeriveInner();
		NewCross();
	}
	
	private final void
	NewDeriveInner()
	{
		if( DownDivFace().numHalf > maxNumInner ) {
			DeriveDivFace().inner = new lvDivFaceType.DeriveInner[ DownDivFace().numHalf ];
			for( int i=0; i<maxNumInner; i++ )
				DeriveDivFace().inner[ i ] = Gbl().staticInner[ i ];
			for( int i=maxNumInner; i<DownDivFace().numHalf; i++ )
				DeriveDivFace().inner[ i ] = new lvDivFaceType.DeriveInner( global );
		}
		else
			DeriveDivFace().inner = Gbl().staticInner;
	}
	
	private final void
	NewCross()
	{
		if( DownDivFace().numHalf > maxNumCross ) {
			Gbl().tmpMakeInnerEdge.cross = new TmpCross[ DownDivFace().numHalf ];
			for( int i=0; i<maxNumCross; i++ )
				Gbl().tmpMakeInnerEdge.cross[ i ] = Gbl().svCross[ i ];
			for( int i=maxNumCross; i<DownDivFace().numHalf; i++ )
				Gbl().tmpMakeInnerEdge.cross[ i ] = new TmpCross( global );
		}
		else
			Gbl().tmpMakeInnerEdge.cross = Gbl().svCross;
	}
	
	private final void
	ExecMain() throws lvThrowable
	{
		ExecInner0();
		ExecCenter();
		ExecInner1();
		CorrectInnerHandVec();
	}
	
	private final void
	ExecInner0() throws lvThrowable
	{
		lvMakeCbd.DownCbd    downCbd   = Gbl().tdExecInner0[ 0 ];		// downCbd   = lvMakeCbd.DownCbd( global );
		lvMakeCbd.UpHalfCbd  upHalfCbd = Gbl().tuExecInner0[ 0 ];		// upHalfCbd = lvMakeCbd.UpHalfCbd( global );
		
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			SetDownCbd( i, downCbd );
			Gbl().makeCbd.ExecHalf( downCbd, upHalfCbd );
			ExecInner0Main( i, upHalfCbd );
		}
	}
	
	private final void
	SetDownCbd( int halfNo, lvMakeCbd.DownCbd downCbd )
	{
		int  halfB = ( halfNo + DownDivFace().numHalf - 1 ) % DownDivFace().numHalf;
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		
		downCbd.info[ 0 ].vtx.Assign( DownDivFace().half[ halfNo ].pos );
		downCbd.info[ 1 ].vtx.Assign( DownDivFace().half[ halfF  ].pos );
		
		downCbd.info[ 0 ].cVec.Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		downCbd.info[ 1 ].cVec.Assign( DownDivFace().half[ halfNo ].handVec[ 1 ].Neg() );
		
		downCbd.info[ 0 ].bCbd.Assign( DownDivFace().half[ halfB ].handVec[ 1 ] );
		downCbd.info[ 1 ].bCbd.Assign( DownDivFace().half[ halfF ].handVec[ 0 ] );
		
		downCbd.faceNormal.Assign( DownDivFace().center.normal );
		
		if( DownDivFace().numHalf == 3 ) {
			downCbd.antiCenter = Gbl().antiCenter;
			downCbd.antiCenter.Assign( DownDivFace().half[ halfB ].pos );
			
			downCbd.info[ 0 ].antiVtx = Gbl().antiVtx0;
			downCbd.info[ 1 ].antiVtx = Gbl().antiVtx1;
			downCbd.info[ 0 ].antiVtx.Assign( downCbd.antiCenter );
			downCbd.info[ 1 ].antiVtx.Assign( downCbd.antiCenter );
		}
		else {
			downCbd.antiCenter        = null;
			downCbd.info[ 0 ].antiVtx = null;
			downCbd.info[ 1 ].antiVtx = null;
		}
	}
	
	private final void
	ExecInner0Main( int halfNo, lvMakeCbd.UpHalfCbd upHalfCbd ) throws lvThrowable
	{
		lvVector  cVec = Gbl().tvExecInner0Main[ 0 ];			// cVec = new lvVector( global );
		
		lvDivFaceLow.Position( halfNo, 0.5, Gbl().tmpMakeInnerEdge.cross[ halfNo ].pos );

		lvVector  aCbd = upHalfCbd.cbd[ 1 ].aCbd;
		double    k    = upHalfCbd.cbd[ 1 ].k;
		double    h    = upHalfCbd.cbd[ 1 ].h;
		
		lvDivFaceLow.Derivative( halfNo, 0.5, cVec );
		cVec.DivAssign( 6.0 );
		
		lvVector  bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ halfNo ].bCbdDim2;
		bCbdDim2.Assign( ( aCbd.Mul( k ) ).Add( cVec.Mul( h ) ) );
		bCbdDim2.MulAssign( 3.0/2.0 );
	}
	
	private final void
	ExecCenter() throws lvThrowable
	{
		ExecCenterPos();
		ExecCenterNormal();
	}
	
	private final void
	ExecCenterPos() throws lvThrowable
	{
		double  sum = ExecCenterPos0();
		ExecCenterPos1( sum );
	}
	
	private final double
	ExecCenterPos0() throws lvThrowable
	{
		lvVector  crossPos0 = Gbl().tvExecCenterPos0[ 0 ];					// crossVec0 = new lvVector( global );
		lvVector  crossPos1 = Gbl().tvExecCenterPos0[ 1 ];					// crossVec1 = new lvVector( global );
		
		double  sum = 0.0;
		lvVector  pos, bCbdDim2;
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			pos      = Gbl().tmpMakeInnerEdge.cross[ i ].pos;
			bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ i ].bCbdDim2;
			crossPos0.Assign( pos.Add( bCbdDim2 ) );
			
			int  ii = ( i + 1 ) % DownDivFace().numHalf;
			pos      = Gbl().tmpMakeInnerEdge.cross[ ii ].pos;
			bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ ii ].bCbdDim2;
			crossPos1.Assign( pos.Add( bCbdDim2 ) );
			
			sum += ( crossPos0.Sub( crossPos1 ) ).Length();
		}
		
		return sum;
	}
	
	private final void
	ExecCenterPos1( double sum ) throws lvThrowable
	{
		lvVector  crossPos0 = Gbl().tvExecCenterPos1[ 0 ];					// crossVec0 = new lvVector( global );
		lvVector  crossPos1 = Gbl().tvExecCenterPos1[ 1 ];					// crossVec1 = new lvVector( global );
		lvVector  ave       = Gbl().tvExecCenterPos1[ 2 ];					// ave       = new lvVector( global );

		DeriveDivFace().center.pos.SetXYZ( 0.0, 0.0, 0.0 );
		lvVector  pos, bCbdDim2;
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			pos      = Gbl().tmpMakeInnerEdge.cross[ i ].pos;
			bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ i ].bCbdDim2;
			crossPos0.Assign( pos.Add( bCbdDim2 ) );

			int  ii = ( i + 1 ) % DownDivFace().numHalf;
			pos      = Gbl().tmpMakeInnerEdge.cross[ ii ].pos;
			bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ ii ].bCbdDim2;
			crossPos1.Assign( pos.Add( bCbdDim2 ) );

			ave.Assign( ( crossPos0.Add( crossPos1 ) ).Div( 2.0 ) );
			ave.MulAssign( ( crossPos0.Sub( crossPos1 ) ).Length() );
			DeriveDivFace().center.pos.AddAssign( ave );					// DeriveDivFace().center.pos += crossVec;
		}
		
		DeriveDivFace().center.pos.DivAssign( sum );						// DeriveDivFace().center.pos /= sum;
	}
	
	private final void
	ExecCenterNormal() throws lvThrowable
	{
		lvVector  edgeNormal = Gbl().tvExecCenterNormal[ 0 ];				// edgeNormal = new lvVector( global );
		
		lvVector  stdEdge[] = NewStdEdgeNormal();
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			lvVector  pos      = Gbl().tmpMakeInnerEdge.cross[ i ].pos;
			lvVector  bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ i ].bCbdDim2;
			stdEdge[ i ].Assign( pos.Add( bCbdDim2 ) );
		}
		edgeNormal.Normal( stdEdge, DownDivFace().numHalf );
		DeriveDivFace().center.normal.Assign( ( ( edgeNormal.Add( DownDivFace().center.normal ) ).Div( 2.0 ) ).Unit() );
							// DeriveDivFace().center.normal = ( ( edgeNormal + DownDivFace().center.normal ) / 2.0 ).Unit();
	}
	
	private final lvVector[]
	NewStdEdgeNormal()
	{
		lvVector  stdEdge[];
		if( DownDivFace().numHalf > maxNumStdEdge ) {
			stdEdge = new lvVector[ DownDivFace().numHalf ];
			for( int i=0; i<maxNumStdEdge; i++ )
				stdEdge[ i ] = Gbl().svStdEdge[ i ];
			for( int i=maxNumStdEdge; i<DownDivFace().numHalf; i++ )
				stdEdge[ i ] = new lvVector( global );
		}
		else
			stdEdge = Gbl().svStdEdge;
			
		return  stdEdge;
	}
		
	private final void
	ExecInner1()
	{
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			lvVector  pos      = Gbl().tmpMakeInnerEdge.cross[ i ].pos;
			lvVector  bCbdDim2 = Gbl().tmpMakeInnerEdge.cross[ i ].bCbdDim2;
			lvVector  handVec0 = DeriveDivFace().inner[ i ].handVec[ 0 ];
			lvVector  handVec1 = DeriveDivFace().inner[ i ].handVec[ 1 ];
			
			handVec0.Assign( bCbdDim2.Mul( 2.0 / 3.0 ) );
			handVec1.Assign( ( ( pos.Add( bCbdDim2 ) ).Sub( DeriveDivFace().center.pos ) ).Mul( 2.0 / 3.0 ) );
								// handVec1 = ( ( pos + bCbdDim2 ) - DeriveDivFace().center.pos ) * ( 2.0 / 3.0 );
		}
	}
	
	private final void
	CorrectInnerHandVec()
	{
		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			lvVector  handVec = DeriveDivFace().inner[ i ].handVec[ 1 ];
			lvVector  normal  = DeriveDivFace().center.normal;
			
			handVec.SubAssign( ( normal.DotR( handVec ) ).Mul( normal ) );
							// handVec -= ( normal * handVec ) * normal;
		}
	}
	
	private final void
	Finish()
	{
		Gbl().tmpMakeInnerEdge.cross = null;		// Delete( Gbl().tmpMakeInnerEdge.cross );
	}

}
