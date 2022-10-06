//
// Copyright (C) 1998-2001 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakePoly.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * 1�̃V�F�����i�q�i lvModel.shell[ n ].poly�u n �̓V�F��No �v�j���쐬����N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * @author	  debuged by Eishin Matsui (01/07/12)
 * 
 */
public class lvMakePoly extends lvRoot {
	
	/**
	 * ���_�ɑ΂���ꎞ�I�����N���X�iTmpPoly�p�j
	 */
	private static class TmpVertex {
		
		/**
		 * radialSeq[] �ɑ΂���A�J�n�_�ƌ��iradial���j������ --- �����l null		<br>
		 * radial���́A�ŏIradial���������͉�radial���i�ŏIradial��*2 �܂��́A(�ŏIradial��-1)*2 �j
		 */
		private lvRec.SeqPart  radial	= new lvRec.SeqPart();
		
		/**
		 * SetTmpRadialSeq0()�p�̈ꎞ�I�J�E���^
		 */
		private int            tmpCnt;
		
		/**
		 * ���̒��_���i��constric�ȁjNG�ʂ������ǂ����H	�����Ȃ�:false, ����:true
		 */
		private boolean        hasNg;
	}
	
	/**
	 * radial �ɑ΂���ꎞ�I�����N���X�iTmpPoly�p�j
	 */
	private static class TmpRadial {
		
		/** ���� radial �ɑ΂��āA�΂ƂȂ钸�_�̔ԍ�		*/
		private int      mateVtxNo;
		
		/**
		 * radial�ɂ��Ő��������s�����ꍇ�A�ŏ��ɔ�������邩�������B 2���:false, �ŏ�:true		<br>
		 * �i �΂ƂȂ钸�_�� radial �Ɋւ��Ă͗Ő������̕K�v�͂Ȃ� �j
		 */
		private boolean  firstLook;
		
		/** lvToKernel.edge[]�� No ���L�q����B�����ꍇ�́A-1		*/
		private int      srcEdgeNo;
		
		/** TmpEdge.edge[]�� No ���L�q����B�����l�́A-1			*/
		private int      dstEdgeNo;
		/** TmpEdge.edge[]�̎n�_���A��radial�̒��_���ǂ����H	�n�_=���_:false, �I�_=���_:true		*/
		private boolean  dstEdgeReverse;

		// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
		private static final void
		Copy( TmpRadial src, TmpRadial dst )
		{
			dst.mateVtxNo      = src.mateVtxNo;
			dst.firstLook      = src.firstLook;
			dst.srcEdgeNo      = src.srcEdgeNo;
			dst.dstEdgeNo      = src.dstEdgeNo;
			dst.dstEdgeReverse = src.dstEdgeReverse;
		}
	}
	
	/**
	 * �ʂɑ΂���ꎞ�I�����N���X�iTmpPoly�p�j
	 */
	private static class TmpFace {
		
		/**
		 * �ʂ̊J�n�_�ƌ��i���_���i3�ȏ�j)�̔z��iGS��+NG�ʁiconstric�̏ꍇ�j�̐��������݂���u 1�ȏ� �v�j --- �����l null
		 */
		private lvRec.SeqPart  vertex	= new lvRec.SeqPart();
	}
	
	/**
	 * �i��constric�́jNG�ʂ������_�ɑ΂���ꎞ�I�����N���X�iTmpPoly�p�j
	 */
	private static class TmpNgVertex {
		
		/**
		 * �i��constric�́jNG�ʂ������_�̔ԍ�
		 */
		private int      vertexNo;
		
		/**
		 * SchNgVertex()�p�̃t���O
		 */
		private boolean  alreadyExec;
	}
	
	/**
	 * �Ő��ɑ΂���ꎞ�I�����N���X�iTmpPoly�p�j
	 */
	private static class TmpEdge {
		
		/** ���_No�i�n�_�j				*/
		private int		v0;
	
		/** ���_No�i�I�_�j				*/
		private int		v1;
	}

// -------------------------------------------------------------------
	
	/**
	 * �V�F�����i�q�쐬�̍ہA�ꎞ�I�Ɏg�p������̂��߂̓����N���X
	 */
	private static class TmpPoly {
		
		/**
		 * radialSeq[] �ɑ΂���A�J�n�_�ƌ��iradial���j������ --- �����l null						<br>
		 * radial���́A�ŏIradial���������͉�radial���i�ŏIradial��*2 �܂��́A(�ŏIradial��-1)*2 �j
		 */
		private TmpVertex  vertex[]				= null;
		
		/**
		 * ���_��n����ꍇ�A															<br>
		 * ���_0�̉�radialNo��, ���_1�̉�radialNo��, ---, ���_( n-1 )�̉�radialNo��		<br>
		 * �Ƒ����B�z��̒����́A														<br>
		 * ���_0�̉�radial�� + ���_1�̉�radial�� + --- + ���_( n-1 )�̉�radial��		<br>
		 * �ƂȂ�B --- �����l null														<br>
		 */
		private TmpRadial  radialSeq[]			= null;
		
		/**
		 * 1�̒��_�̉�radialNo��B�z��̒����́A1�̒��_�̉�radialNo��̓��A�ő咷�̂��̂ƂȂ� --- �����l null
		 */
		private TmpRadial  radialOneVtx[]		= null;
		
		/**
		 * CorrectRadialMain()�̈ꎞ�I�Ȕz��B�z��̒����́A1�̒��_�̉�radialNo��̓��A�ő咷�̂��̂� 1/2 �ƂȂ� --- �����l null
		 */
		private boolean    alreadyExecOneVtx[]	= null;
		
		/**
		 * �ʂ̊J�n�_�ƌ��i���_���i3�ȏ�j)�̔z��iGS��+NG�ʁiconstric�̏ꍇ�j�̐��������݂���u 1�ȏ� �v�j --- �����l null
		 */
		private TmpFace    face[]				= null;

		/**
		 * �ʂ�n����ꍇ�A											<br>
		 * ��0�̒��_No��, ��1�̒��_No��, ---, ��( n-1 )�̒��_No��		<br>
		 * �Ƒ����B�z��̒����́A										<br>
		 * ��0�̒��_�� + ��1�̒��_�� + --- + ��( n-1 )�̒��_��			<br>
		 * �ƂȂ�B --- �����l null
		 */
		private int	faceVtxSeq[]				= null;
		
		/**
		 * �i��constric�́jNG�ʂ������_�Ɋւ�����z��B					<br>
		 *  hasNg == true �̒��_�̐��������݂���B --- �����l null
		 */
		private TmpNgVertex  srcNgVtxSeq[]		= null;

		/** ngVtxSeq[]�̔z�񒷁BNG�ʂ̐��ƂȂ�B		*/
		private int        numNgFace			= 0;
		/**
		 * NG�ʁi��constric�̏ꍇ�j�̊J�n�_�ƌ��i���_���i3�ȏ�j)�̔z��B			<br>
		 * �uhasNg == true �̒��_�̐��v��1/3�̐��A���݂���iNG�ʂ̐���葽���j�B	<br>
		 * �K�v�Ȓ����́ANG�ʂ̐������ł���--- �����l null							<br>
		 * ���j�uhasNg == true �̒��_�̐��v��1/3�Ƃ́ANG�ʂ����ׂĎO�p�`�̂Ƃ���NG�ʐ��ł���B
		 */
		private TmpFace    ngFace[]				= null;

		/**
		 * �i��constric�́jNG�ʓ��̒��_No�𗅗񂵂��z��B					<br>
		 *  hasNg == true �̒��_�̐��������݂���B							<br>
		 * NG�ʂ�n����ꍇ�A												<br>
		 * NG��0�̒��_No��, NG��1�̒��_No��, ---, NG��( n-1 )�̒��_No��		<br>
		 * �Ƒ����B�z��̒����́A											<br>
		 * NG��0�̒��_�� + NG��1�̒��_�� + --- + NG��( n-1 )�̒��_��		<br>
		 * �ƂȂ�B --- �����l null
		 */
		private int  dstNgVtxSeq[]				= null;
		
		/**
		 * �Ő��̗����_�������z��i�Ő����������݂���u 3�ȏ� �v�j --- �����l null
		 */
		private TmpEdge  edge[]					= null;
	}
	
// -------------------------------------------------------------------
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** �V�F���쐬�̍ہA�ꎞ�I�Ɏg�p������		*/
		private TmpPoly        tmpPoly			= null;
		
		/** �J�����g�V�F��No							*/
		private int            curShellNo		= 0;
		/** �J�����glvToKernel�f�[�^					*/
		private lvToKernelLow  curSrc			= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt ) {
			tmpPoly = new TmpPoly();
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lvTopoGblElm )global.GTopo() ).gMakePoly;
	}
	/** TmpPoly�p�̃O���[�o���f�[�^				*/
	private final TmpPoly
	TmpPoly()
	{
		return  Gbl().tmpPoly;
	}
	/** �\�[�X�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvToKernelLow
	CurSrc()
	{
		return  Gbl().curSrc;
	}
	/** �f�X�g�l�[�V�����f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvPolygon
	CurDst()
	{
		return ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvMakePoly( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̃V�F�����i�q�i lvModel.shell[ n ].poly�u n �̓V�F��No �v�j���쐬����
	 * @param  shellNo		(( I )) �V�F��No
	 * @param  src			(( I )) �\�[�X�I�u�W�F�N�g
	 */
	public final void
	Exec( int shellNo, lvToKernelLow src ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		Gbl().curSrc     = src;
		
		VertexProc0();
		RadialProc0();
		FaceProc0();
		RadialProc1();
		EdgeProc0();
		FaceProc1();
		EdgeProc1();
		
		Finish();
	}
	
	private final void
	VertexProc0() throws lvThrowable
	{
		int  num = CurSrc().vertex.length;
		Err().Assert( ( num >= 3 ), "lvMakePoly.VertexProc0(0)" );
		CurDst().NewVertex( num );
		
		SetVetrexInfo();
	}
	
	private final void
	SetVetrexInfo()
	{
		int  num = CurSrc().vertex.length;
		for( int i=0; i<num; i++ ) {
			lvVecDt.Copy( CurSrc().vertex[ i ].pos, CurDst().vertex[ i ].pos );
			if( CurSrc().vertex[ i ].enable == true )
				CurDst().vertex[ i ].round  = CurSrc().vertex[ i ].round;
			else
				CurDst().vertex[ i ].round  = 1.0;
		}
	}
	
	private final void
	RadialProc0() throws lvThrowable
	{
		int  num, numNgVtx, numNgVtxSeq;
		
		Err().Assert( ( CurSrc().gsNumVtx.length >= 1 ), "lvMakePoly.RadialProc0(0)" );
		if( CurSrc().ngNumVtx == null )
			numNgVtx = 0;
		else
			numNgVtx = CurSrc().ngNumVtx.length;
		num = CurSrc().gsNumVtx.length + numNgVtx;
		NewTmpFace( num );
		SetTmpFace();
		
		if( CurSrc().ngVtxSeq == null )
			numNgVtxSeq = 0;
		else
			numNgVtxSeq = CurSrc().ngVtxSeq.length;
		num = CurSrc().gsVtxSeq.length + numNgVtxSeq;
		NewTmpFaceVtxSeq( num );
		SetTmpFaceVtxSeq();

		num = CurSrc().vertex.length;
		NewTmpVertex( num );
		SetTmpVertex();
		
		num = GetNumTmpRadialSeq();
		NewTmpRadialSeq( num );
		SetTmpRadialSeq0();
		
		num = GetMaxTmpRadialSeq();
		NewTmpRadialOneVtx( num );
		NewTmpAlreadyExecOneVtx( num/2 );
		
		CorrectRadialInfo0();

		SetVtxFace();
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().face�� new ���鎞�̒���
	 */
	private final void
	NewTmpFace( int num )
	{
		TmpPoly().face = new TmpFace[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().face[ i ] = new TmpFace();
	}
	
	private final void
	SetTmpFace() throws lvThrowable
	{
		int  num;
		int  cnt = 0;
		
		num = CurSrc().gsNumVtx.length;
		for( int i=0; i<num; i++ ) {
			Err().Assert( ( CurSrc().gsNumVtx[ i ] >= 3 ), "lvMakePoly.SetTmpFace(0)" );
			TmpPoly().face[ cnt ].vertex.num = CurSrc().gsNumVtx[ i ];
			cnt++;
		}
		
		if( CurSrc().ngNumVtx == null )
			num = 0;
		else
			num = CurSrc().ngNumVtx.length;
		for( int i=0; i<num; i++ ) {
			Err().Assert( ( CurSrc().ngNumVtx[ i ] >= 3 ), "lvMakePoly.SetTmpFace(1)" );
			TmpPoly().face[ cnt ].vertex.num = CurSrc().ngNumVtx[ i ];
			cnt++;
		}
		
		cnt = 0;
		num = TmpPoly().face.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().face[ i ].vertex.start = cnt;
			cnt += TmpPoly().face[ i ].vertex.num;
		}
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().faceVtxSeq�� new ���鎞�̒���
	 */
	private final void
	NewTmpFaceVtxSeq( int num )
	{
		TmpPoly().faceVtxSeq = new int[ num ];
	}

	private final void
	SetTmpFaceVtxSeq()
	{
		int  num;
		int  cnt = 0;
		
		num = CurSrc().gsVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().faceVtxSeq[ cnt ] = CurSrc().gsVtxSeq[ i ];
			cnt++;
		}
		
		if( CurSrc().ngVtxSeq == null )
			num = 0;
		else
			num = CurSrc().ngVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().faceVtxSeq[ cnt ] = CurSrc().ngVtxSeq[ i ];
			cnt++;
		}
	}

	/**
	 * @param  num		(( I )) TmpPoly().vertex�� new ���鎞�̒���
	 */
	private final void
	NewTmpVertex( int num )
	{
		TmpPoly().vertex = new TmpVertex[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ] = new TmpVertex();
	}

	private final void
	SetTmpVertex() throws lvThrowable
	{
		int  num, cnt;
		
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ].radial.num = 0;
			
		int  numFace = TmpPoly().face.length;
		for( int i=0; i<numFace; i++ ) {
			int  numVtx = TmpPoly().face[ i ].vertex.num;
			int  pos    = TmpPoly().face[ i ].vertex.start;
			for( int j=0; j<numVtx; j++ ) {
				int  vtxNo = TmpPoly().faceVtxSeq[ pos ];
				pos++;
				TmpPoly().vertex[ vtxNo ].radial.num += 2;
			}
		}

		cnt = 0;
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().vertex[ i ].radial.start = cnt;
			Err().Assert( ( TmpPoly().vertex[ i ].radial.num >= 2 ), "lvMakePoly.SetTmpVertex(0)" );
			cnt += TmpPoly().vertex[ i ].radial.num;
		}
	}

	/**
	 * @return			TmpPoly().radialSeq�� new ���鎞�̒����𓾂�
	 */
	private final int
	GetNumTmpRadialSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			cnt += TmpPoly().vertex[ i ].radial.num;
			
		return  cnt;
	}

	/**
	 * @param  num		(( I )) TmpPoly().radialSeq�� new ���鎞�̒���
	 */
	private final void
	NewTmpRadialSeq( int num )
	{
		TmpPoly().radialSeq = new TmpRadial[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ] = new TmpRadial();
	}
	
	private final void
	SetTmpRadialSeq0()
	{
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ].tmpCnt = 0;
			
		int  numFace = TmpPoly().face.length;
		for( int i=0; i<numFace; i++ ) {
			int  numVtx = TmpPoly().face[ i ].vertex.num;
			int  start  = TmpPoly().face[ i ].vertex.start;
			for( int j=0; j<numVtx; j++ ) {
				int  vtxB  = TmpPoly().faceVtxSeq[ start + ( j+numVtx-1 ) % numVtx ];
				int  vtxNo = TmpPoly().faceVtxSeq[ start + j ];
				int  vtxF  = TmpPoly().faceVtxSeq[ start + ( j+1 ) % numVtx ];
				
				int  cnt = TmpPoly().vertex[ vtxNo ].radial.start + TmpPoly().vertex[ vtxNo ].tmpCnt;
				TmpPoly().radialSeq[ cnt   ].mateVtxNo = vtxF;
				TmpPoly().radialSeq[ cnt+1 ].mateVtxNo = vtxB;
				TmpPoly().vertex[ vtxNo ].tmpCnt += 2;
			}
		}
	}

	/**
	 * @return			TmpPoly().radialOneVtx�� new ���鎞�̒����𓾂�
	 */
	private final int
	GetMaxTmpRadialSeq()
	{
		int  max = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( max < TmpPoly().vertex[ i ].radial.num )
				max = TmpPoly().vertex[ i ].radial.num;
		}
			
		return  max;
	}

	/**
	 * @param  num		(( I )) TmpPoly().radialOneVtx�� new ���鎞�̒���
	 */
	private final void
	NewTmpRadialOneVtx( int num )
	{
		TmpPoly().radialOneVtx = new TmpRadial[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().radialOneVtx[ i ] = new TmpRadial();
	}

	/**
	 * @param  num		(( I )) TmpPoly().alreadyExecOneVtx�� new ���鎞�̒���
	 */
	private final void
	NewTmpAlreadyExecOneVtx( int num )
	{
		TmpPoly().alreadyExecOneVtx = new boolean[ num ];
	}

	private final void
	CorrectRadialInfo0() throws lvThrowable
	{
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			lvRec.SeqPart  radial    = TmpPoly().vertex[ i ].radial;
			int            posFace   = SchRadialFirstPosition( radial );
			int            numRadial = CorrectRadialMain( radial, posFace );
			boolean        hasNg     = HasNg( numRadial );
			if( hasNg == false )
				numRadial--;
			TmpPoly().vertex[ i ].radial.num = numRadial;
			TmpPoly().vertex[ i ].hasNg      = hasNg;
			RadialOneVtxToSeq( radial.start, numRadial );
		}
	}
	
	/**
	 * @param  radial		(( I )) TmpPoly().vertex[ * ].radial�I�u�W�F�N�g
	 * @return				GS�ʂ�NG�ʁi��constric�̏ꍇ�j�̐؂�ڂ̈ʒu
	 */
	private final int
	SchRadialFirstPosition( lvRec.SeqPart radial ) throws lvThrowable
	{
		int  numFace = radial.num / 2;
		Err().Assert( ( numFace > 0 ), "lvMakePoly.SchRadialFirstPosition(0)" );
		if( numFace <= 1 )
			return  0;
		
		boolean  chk;
		int      posFace = 0;
		for( int i=0; i<numFace; i++ ) {
			chk = false;
			for( int j=0; j<numFace; j++ ) {
				if( i == j )
					continue;
				int  i2 = i * 2;
				int  j2 = j * 2 + 1;
				int  vtx0 = TmpPoly().radialSeq[ radial.start + i2 ].mateVtxNo;
				int  vtx1 = TmpPoly().radialSeq[ radial.start + j2 ].mateVtxNo;
				if( vtx0 == vtx1 ) {
					chk = true;
					break;
				}
			}
			if( chk == false ) {
				posFace = i;
				break;
			}
		}
		
		return  posFace;
	}

	/**
	 * @param  radial		(( I )) TmpPoly().vertex[ * ].radial�I�u�W�F�N�g
	 * @param  posFace		GS�ʂ�NG�ʁi��constric�̏ꍇ�j�̐؂�ڂ̈ʒu
	 * @return				���̃��W�A�����𓾂�
	 */
	private final int
	CorrectRadialMain( lvRec.SeqPart radial, int posFace ) throws lvThrowable
	{
		int  numFace = radial.num / 2;
		for( int i=0; i<numFace; i++ )
			TmpPoly().alreadyExecOneVtx[ i ] = false;
		
		TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( posFace*2     ) ], TmpPoly().radialOneVtx[ 0 ]  );
		TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( posFace*2 + 1 ) ], TmpPoly().radialOneVtx[ 1 ]  );
		
		TmpPoly().alreadyExecOneVtx[ posFace ] = true;
		
		boolean  chk;
		int      pos = posFace;
		for( int i=1; i < numFace; i++ ) {
			chk = false;
			for( int j=0; j<numFace; j++ ) {
				if( pos == j || TmpPoly().alreadyExecOneVtx[ j ] == true )
					continue;
				int  i2 = pos * 2 + 1;
				int  j2 =   j * 2;
				int  vtx0 = TmpPoly().radialSeq[ radial.start + i2 ].mateVtxNo;
				int  vtx1 = TmpPoly().radialSeq[ radial.start + j2 ].mateVtxNo;
				if( vtx0 == vtx1 ) {
					pos = j;
					chk = true;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.CorrectRadialMain(0)" );
			TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( pos*2 + 1 ) ], TmpPoly().radialOneVtx[ i+1 ] );
			TmpPoly().alreadyExecOneVtx[ pos ] = true;
		}
		
		return  numFace + 1;
	}
	
	/**
	 * @param  radial		(( I )) ���̃��W�A����
	 * @return				�i��constric�́jNG�ʂ������H	�����Ȃ�:false, ����:true
	 */
	private final boolean
	HasNg( int num )
	{
		if( TmpPoly().radialOneVtx[ 0 ].mateVtxNo != TmpPoly().radialOneVtx[ num-1 ].mateVtxNo )
			return  true;
			
		return  false;
	}

	/**
	 * @param  start		(( I )) TmpPoly().radialSeq ���̊J�n�_
	 * @param  num			(( I )) �i�^�́j���W�A����
	 */
	private final void
	RadialOneVtxToSeq( int start, int num )
	{
		for( int i=0; i<num; i++ )
			TmpRadial.Copy( TmpPoly().radialOneVtx[ i ], TmpPoly().radialSeq[ start+i ] );
	}
	
	private final void
	SetVtxFace()
	{
		int  num, cnt;
		
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			CurDst().vertex[ i ].vtxFace.num = TmpPoly().vertex[ i ].radial.num;

		cnt = 0;
		num = CurDst().vertex.length;
		for( int i=0; i<num; i++ ) {
			CurDst().vertex[ i ].vtxFace.start = cnt;
			cnt += CurDst().vertex[ i ].vtxFace.num;
		}
	}

	private final void
	FaceProc0() throws lvThrowable
	{
		int  num, numDstNgVtxSeq;

		MakeNg();
		
		CurDst().ngStartNo = CurSrc().gsNumVtx.length;

		num = TmpPoly().face.length + TmpPoly().numNgFace;
		CurDst().NewFace( num );
		SetFaceInfo();
		
		if( TmpPoly().dstNgVtxSeq == null )
			numDstNgVtxSeq = 0;
		else
			numDstNgVtxSeq = TmpPoly().dstNgVtxSeq.length;
		num = TmpPoly().faceVtxSeq.length + numDstNgVtxSeq;
		CurDst().NewFaceHalfSeq( num );
		SetFaceHalfSeq();
		
		SetFaceHalfRadial();
	}
	
	private final void
	MakeNg() throws lvThrowable
	{
		int  num = GetNumTmpNgVtxSeq();
		if( num == 0 )
			return;
			
		NewTmpNgFace( num/3 );
		NewTmpDstNgVtxSeq( num );
		
		NewTmpSrcNgVtxSeq( num );
		InitTmpSrcNgVtxSeq();
		
		TmpPoly().numNgFace = MakeNgMain();
	}
	
	/**
	 * @return			TmpPoly().dstNgVtxSeq, TmpPoly().srcNgVtxSeq �� new ���鎞�̒����𓾂�
	 */
	private final int
	GetNumTmpNgVtxSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().vertex[ i ].hasNg == true )
				cnt++;
		}
			
		return  cnt;
	}

	/**
	 * @param  num		(( I )) TmpPoly().ngFace�� new ���鎞�̒���
	 */
	private final void
	NewTmpNgFace( int num )
	{
		TmpPoly().ngFace = new TmpFace[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().ngFace[ i ] = new TmpFace();
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().dstNgVtxSeq�� new ���鎞�̒���
	 */
	private final void
	NewTmpDstNgVtxSeq( int num )
	{
		TmpPoly().dstNgVtxSeq = new int[ num ];
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().srcNgVtxSeq�� new ���鎞�̒���
	 */
	private final void
	NewTmpSrcNgVtxSeq( int num )
	{
		TmpPoly().srcNgVtxSeq = new TmpNgVertex[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().srcNgVtxSeq[ i ] = new TmpNgVertex();
	}
	
	/**
	 * @return			TmpPoly().srcNgVtxSeq �̏������������̒���
	 */
	private final int
	InitTmpSrcNgVtxSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().vertex[ i ].hasNg == true ) {
				TmpPoly().srcNgVtxSeq[ cnt ].vertexNo    = i;
				TmpPoly().srcNgVtxSeq[ cnt ].alreadyExec = false;
				cnt++;
			}
		}
			
		return  cnt;
	}

	/**
	 * @return			TmpPoly().numNgFace�̔z�񒷂�Ԃ��BNG�ʂ̐��ƂȂ�B
	 */
	private final int
	MakeNgMain() throws lvThrowable
	{
		int  cnt  = 0;
		int  ngNo = 0;
		
		int  num = TmpPoly().srcNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().srcNgVtxSeq[ i ].alreadyExec == true )
				continue;
			Err().Assert( ( ngNo < TmpPoly().ngFace.length ), "lvMakePoly.MakeNgMain(0)" );
			cnt = MakeNgOne( i, cnt, ngNo );
			ngNo++;
		}
		
		return  ngNo;
	}
	
	/**
	 * @param  posStart		(( I )) TmpPoly().srcNgVtxSeq �̊J�n�_�C���f�b�N�X
	 * @param  cntStart		(( I )) TmpPoly().dstNgVtxSeq �̊J�n�_�C���f�b�N�X
	 * @param  ngNo			(( I )) TmpPoly().ngFace �̃C���f�b�N�X
	 * @return				TmpPoly().dstNgVtxSeq �̎����J�n�_�C���f�b�N�X
	 */
	private final int
	MakeNgOne( int posStart, int cntStart, int ngNo ) throws lvThrowable
	{
		lvRec.SeqPart  radial;
		int  vtx1 = 0;
		
		int  cnt  = cntStart;
		int  nVtx = 0;
		int  pos  = posStart;
		int  vtxOrg = TmpPoly().srcNgVtxSeq[ posStart ].vertexNo;

		boolean  chk2 = false;
		int  num = TmpPoly().srcNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			int  vtx0 = TmpPoly().srcNgVtxSeq[ pos ].vertexNo;
			TmpPoly().dstNgVtxSeq[ cnt ] = vtx0;
			cnt++;
			nVtx++;
			TmpPoly().srcNgVtxSeq[ pos ].alreadyExec = true;
			
			radial = TmpPoly().vertex[ vtx0 ].radial;
			int  mate0 = TmpPoly().radialSeq[ radial.start + ( radial.num-1 ) ].mateVtxNo;
				
			boolean  chk = false;
			for( int j=0; j<num; j++ ) {
				if( pos == j || ( TmpPoly().srcNgVtxSeq[ j ].alreadyExec == true && posStart != j ) )
					continue;
					
				vtx1   = TmpPoly().srcNgVtxSeq[ j ].vertexNo;
				radial = TmpPoly().vertex[ vtx1 ].radial;
				int  mate1 = TmpPoly().radialSeq[ radial.start + 0 ].mateVtxNo;
				
				if( mate0 == vtx1 ) {
					Err().Assert( ( mate1 == vtx0 ), "lvMakePoly.MakeNgOne(0)" );
					pos = j;
					chk = true;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.MakeNgOne(1)" );
			if( chk == true && vtx1 == vtxOrg ) {
				chk2 = true;
				break;
			}
		}
		Err().Assert( ( chk2 == true ), "lvMakePoly.MakeNgOne(2)" );
		Err().Assert( ( nVtx >= 3 ),    "lvMakePoly.MakeNgOne(3)" );
		TmpPoly().ngFace[ ngNo ].vertex.num = nVtx;
		
		return  cnt;
	}

	private final void
	SetFaceInfo()
	{
		int  num;
		int  cnt = 0;
		
		num = TmpPoly().face.length;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ cnt ].num = TmpPoly().face[ i ].vertex.num;
			cnt++;
		}
		
		num = TmpPoly().numNgFace;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ cnt ].num = TmpPoly().ngFace[ i ].vertex.num;
			cnt++;
		}
		
		cnt = 0;
		num = CurDst().face.length;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ i ].start = cnt;
			cnt += CurDst().face[ i ].num;
		}
	}
	
	private final void
	SetFaceHalfSeq()
	{
		int  num;
		int  cnt = 0;
		
		num = TmpPoly().faceVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			CurDst().faceHalfSeq[ cnt ].vtxNo = TmpPoly().faceVtxSeq[ i ];
			cnt++;
		}
		
		if( TmpPoly().dstNgVtxSeq == null )
			num = 0;
		else
			num = TmpPoly().dstNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			CurDst().faceHalfSeq[ cnt ].vtxNo = TmpPoly().dstNgVtxSeq[ i ];
			cnt++;
		}
	}

	private final void
	SetFaceHalfRadial() throws lvThrowable
	{
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  radial = CurDst().face[ i ];
			for( int j=0; j<radial.num; j++ ) {
				int  vtxNo = CurDst().faceHalfSeq[ radial.start + j ].vtxNo;
				int  vtxF  = CurDst().faceHalfSeq[ radial.start + ( ( j+1 ) % radial.num ) ].vtxNo;
				CurDst().faceHalfSeq[ radial.start + j ].radialNo = SetFaceHalfRadialMain( vtxNo, vtxF );
			}
		}
	}

	/**
	 * @param  vtxNo		(( I )) ��ƂȂ钸�_No
	 * @param  vtxB			(( I )) �ʓ��ɂ����ĂP�O�ƂȂ钸�_No
	 * @return				���W�A��No
	 */
	private final int
	SetFaceHalfRadialMain( int vtxNo, int vtxF ) throws lvThrowable
	{
		int      radialNo = 0;
		boolean  chk      = false;
		lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
		for( int i=0; i<radial.num; i++ ) {
			if( vtxF == TmpPoly().radialSeq[ radial.start + i ].mateVtxNo ) {
				radialNo = i;
				chk      = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakePoly.SetFaceHalfRadialMain(0)" );
		
		return  radialNo;
	}
	
	private final void
	RadialProc1()
	{
		int  num = GetNumVtxFaceSeq();
		CurDst().NewVtxFaceSeq( num );
		SetVtxFaceSeq();
	}
	
	/**
	 * @return			CurDst().NewVtxFaceSeq�� new ���鎞�̒����𓾂�
	 */
	private final int
	GetNumVtxFaceSeq()
	{
		int  cnt = 0;
		int  num = CurDst().vertex.length;
		for( int i=0; i<num; i++ )
			cnt += CurDst().vertex[ i ].vtxFace.num;
			
		return  cnt;
	}
	
	private final void
	SetVtxFaceSeq()
	{
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  radial = CurDst().face[ i ];
			for( int j=0; j<radial.num; j++ ) {
				int  vtxNo    = CurDst().faceHalfSeq[ radial.start + j ].vtxNo;
				int  radialNo = CurDst().faceHalfSeq[ radial.start + j ].radialNo;
				
				int  start    = CurDst().vertex[ vtxNo ].vtxFace.start;
				CurDst().vtxFaceSeq[ start + radialNo ].faceNo = i;
				CurDst().vtxFaceSeq[ start + radialNo ].halfNo = j;
			}
		}
	}
	
	private final void
	EdgeProc0() throws lvThrowable
	{
		int  num = CurDst().vtxFaceSeq.length / 2;
		CurDst().NewEdge( num );
		NewTmpEdge( num );
		
		SetTmpEdge();
		
		SetSrcEdgeNo();
		SetDstEdgeNo();
		
		SetEdgeInit();
		SetEdgeInfo();
	}

	/**
	 * @param  num		(( I )) TmpPoly().edge�� new ���鎞�̒���
	 */
	private final void
	NewTmpEdge( int num )
	{
		TmpPoly().edge = new TmpEdge[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().edge[ i ] = new TmpEdge();
	}
	
	private final void
	SetTmpEdge() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].firstLook = false;
		
		int  cnt    = 0;
		int  numVtx = TmpPoly().vertex.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  radial = TmpPoly().vertex[ i ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( TmpPoly().radialSeq[ radial.start + j ].firstLook == true )
					continue;
				Err().Assert( ( cnt < TmpPoly().edge.length ), "lvMakePoly.SetTmpEdge(0)" );
				TmpPoly().edge[ cnt ].v0 = i;
				TmpPoly().edge[ cnt ].v1 = TmpPoly().radialSeq[ radial.start + j ].mateVtxNo;
				TmpPoly().radialSeq[ radial.start + j ].firstLook = true;
				SetMateFirstLook( TmpPoly().edge[ cnt ].v1, TmpPoly().edge[ cnt ].v0 );
				cnt++;
			}
		}
	}
	
	/**
	 * @param  vtxNo		(( I )) ��ƂȂ钸�_No
	 * @param  mateVtxNo	(( I )) �΂ƂȂ钸�_No
	 */
	private final void
	SetMateFirstLook( int vtxNo, int mateVtxNo ) throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
		for( int i=0; i<radial.num; i++ ) {
			if( mateVtxNo == TmpPoly().radialSeq[ radial.start + i ].mateVtxNo ) {
				chk      = true;
				radialNo = i;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakePoly.SetMateFirstLook(0)" );
		
		TmpPoly().radialSeq[ radial.start + radialNo ].firstLook = true;
	}
	
	private final void
	SetSrcEdgeNo() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].srcEdgeNo = -1;
		
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge;
		if( CurSrc().edge == null )
			numEdge = 0;
		else
			numEdge = CurSrc().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = CurSrc().edge[ i ].v0;
			int  v1 = CurSrc().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v0 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v1 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetSrcEdgeNo(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].srcEdgeNo < 0 ), "lvMakePoly.SetSrcEdgeNo(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].srcEdgeNo = i;
		}
	}
	
	private final void
	SetDstEdgeNo() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].dstEdgeNo = -1;
		
		SetDstEdgeNo0();
		SetDstEdgeNo1();
	}
	
	private final void
	SetDstEdgeNo0() throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge = TmpPoly().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = TmpPoly().edge[ i ].v0;
			int  v1 = TmpPoly().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v0 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v1 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetDstEdgeNo0(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo < 0 ), "lvMakePoly.SetDstEdgeNo0(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo      = i;
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse = false;
		}
	}
	
	private final void
	SetDstEdgeNo1() throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge = TmpPoly().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = TmpPoly().edge[ i ].v0;
			int  v1 = TmpPoly().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v1 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v0 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetDstEdgeNo1(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo < 0 ), "lvMakePoly.SetDstEdgeNo1(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo      = i;
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse = true;
		}
	}
	
	private final void
	SetEdgeInit()
	{
		int  numEdge = CurDst().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			CurDst().edge[ i ].allRound = 1.0;
			for( int j=0; j<2; j++ )
				lvVecDt.SetXYZ( 0.0, 1.0, 0.0, CurDst().edge[ i ].vec[ j ] );
		}
	}
	
	private final void
	SetEdgeInfo() throws lvThrowable
	{
		int  numVtx = TmpPoly().vertex.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  radial = TmpPoly().vertex[ i ].radial;
			for( int j=0; j<radial.num; j++ ) {
				int      srcEdgeNo = TmpPoly().radialSeq[ radial.start + j ].srcEdgeNo;
				int      dstEdgeNo = TmpPoly().radialSeq[ radial.start + j ].dstEdgeNo;
				Err().Assert( ( dstEdgeNo >= 0 ), "lvMakePoly.SetEdgeInfo(0)" );
				boolean  reverse   = TmpPoly().radialSeq[ radial.start + j ].dstEdgeReverse;
				SetEdgeMain( srcEdgeNo, dstEdgeNo, reverse );
			}
		}
	}
	
	/**
	 * @param  srcEdgeNo	(( I )) CurSrc().edge �p�C���f�b�N�X
	 * @param  dstEdgeNo	(( I )) CurDst().edge �p�C���f�b�N�X
	 * @param  reverse		(( I )) ���W�A�����S�̒��_No����n�܂��Ă��Ȃ��ꍇ�Atrue
	 */
	private final void
	SetEdgeMain( int srcEdgeNo, int dstEdgeNo, boolean reverse )
	{
		if( srcEdgeNo < 0 )
			return;
		
		if( CurSrc().edge[ srcEdgeNo ].enableAll == true )
			CurDst().edge[ dstEdgeNo ].allRound = CurSrc().edge[ srcEdgeNo ].allRound;

		if( reverse == false ) {
			if( CurSrc().edge[ srcEdgeNo ].vec0 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec0, CurDst().edge[ dstEdgeNo ].vec[ 0 ] );
			if( CurSrc().edge[ srcEdgeNo ].vec1 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec1, CurDst().edge[ dstEdgeNo ].vec[ 1 ] );
		}
		else {
			if( CurSrc().edge[ srcEdgeNo ].vec0 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec0, CurDst().edge[ dstEdgeNo ].vec[ 1 ] );
			if( CurSrc().edge[ srcEdgeNo ].vec1 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec1, CurDst().edge[ dstEdgeNo ].vec[ 0 ] );
		}
	}
	
	private final void
	FaceProc1() throws lvThrowable
	{
		SetFaceHalfSeqEdge();
	}
	
	private final void
	SetFaceHalfSeqEdge() throws lvThrowable
	{
		int  num = CurDst().faceHalfSeq.length;
		for( int i=0; i<num; i++ )
			CurDst().faceHalfSeq[ i ].edgeNo = -1;
		
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = CurDst().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo    = CurDst().faceHalfSeq[ face.start + j ].vtxNo;
				int  radialNo = CurDst().faceHalfSeq[ face.start + j ].radialNo;
			
				lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
				int  edgeNo     = TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo;
				boolean reverse = TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse;
			
				Err().Assert( ( CurDst().faceHalfSeq[ face.start + j ].edgeNo < 0 ), "lvMakePoly.SetFaceHalfSeqEdge(0)" );
				CurDst().faceHalfSeq[ face.start + j ].edgeNo = edgeNo;
				if( reverse == false )
					CurDst().faceHalfSeq[ face.start + j ].edgeIdx = 0;
				else
					CurDst().faceHalfSeq[ face.start + j ].edgeIdx = 1;
			}
		}
	}
	
	private final void
	EdgeProc1() throws lvThrowable
	{
		SetEdgeFace();
	}

	private final void
	SetEdgeFace() throws lvThrowable
	{
		int  num = CurDst().edge.length;
		for( int i=0; i<num; i++ ) {
			for( int j=0; j<2; j++ )
				CurDst().edge[ i ].face[ j ].faceNo = -1;
		}
		
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = CurDst().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  edgeNo  = CurDst().faceHalfSeq[ face.start + j ].edgeNo;
				int  edgeIdx = CurDst().faceHalfSeq[ face.start + j ].edgeIdx;
				
				Err().Assert( ( CurDst().edge[ edgeNo ].face[ edgeIdx ].faceNo < 0 ), "lvMakePoly.SetEdgeFace(0)" );
				CurDst().edge[ edgeNo ].face[ edgeIdx ].faceNo = i;
				CurDst().edge[ edgeNo ].face[ edgeIdx ].halfNo = j;
			}
		}
	}
	
	private final void
	Finish()
	{
		TmpPoly().vertex			= null;		// Delete( TmpPoly().vertex );
		TmpPoly().radialSeq			= null;		// Delete( TmpPoly().radialSeq );
		TmpPoly().radialOneVtx		= null;		// Delete( TmpPoly().radialOneVtx );
		TmpPoly().alreadyExecOneVtx	= null;		// Delete( TmpPoly().alreadyExecOneVtx );
		TmpPoly().face				= null;		// Delete( TmpPoly().face );
		TmpPoly().faceVtxSeq		= null;		// Delete( TmpPoly().faceVtxSeq );
		TmpPoly().srcNgVtxSeq		= null;		// Delete( TmpPoly().srcNgVtxSeq );
		TmpPoly().numNgFace			= 0;
		TmpPoly().ngFace			= null;		// Delete( TmpPoly().ngFace );
		TmpPoly().dstNgVtxSeq		= null;		// Delete( TmpPoly().dstNgVtxSeq );
		TmpPoly().edge				= null;		// Delete( TmpPoly().edge );
		
		Gbl().curSrc    			= null;		// Delete( Gbl().curSrc );
	}
	
}
