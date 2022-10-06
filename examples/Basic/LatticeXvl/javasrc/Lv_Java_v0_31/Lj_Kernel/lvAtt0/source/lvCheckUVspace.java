//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvCheckUVspace.java
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/05/12-)
 * 
 */
public class lvCheckUVspace extends lvRoot {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No						*/
		private int            curShellNo;

		/** �J�����glvToKernelUV�f�[�^				*/
		private lvToKernelUV   srcUVspace			= null;
		
		/**
		 * �eUV��ԓ���GS�ʂ̐��̃J�E���^															<br>
		 *�i�z�񒷂́AlvToKernelUV.numUVspace �Ɠ���������j--- �����l null
		 */
		private int            gsUVcnt[]			= null;

		/**
		 * GS�ʓ��̊e���_�ɂ�������mUV��Ԃ̌�													<br>
		 *�i�z�񒷂́AlvModelPoly.shell[].poly.faceHalfSeq.length �Ɠ���������j--- �����l null
		 */
		private int            gsVtxUV[]			= null;

		/** lvToKernelUV.vtxNumUV �� lvRec.SeqPart��			*/
		private lvRec.SeqPart  vtxNumUV[]			= null;
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Global( lvGlobal dt )
		{
		}
	}


	/** ���N���X�p�̃O���[�o���f�[�^						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGblElm )global.GAtt() ).gCheckUVspace;
	}
	/** lvToKernelUV�f�[�^�p�N���X�I�u�W�F�N�g				*/
	private final lvToKernelUV
	SrcUVspace()
	{
		return  Gbl().srcUVspace;
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
	public lvCheckUVspace( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public final boolean
	NeedUVcalc( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		if( srcUVspace == null )
			return false;
			
		Gbl().curShellNo  = shellNo;
		Gbl().srcUVspace  = srcUVspace;
		
		boolean  result = NeedUVcalcMain( shellNo, srcUVspace );
		
		Finish();
		
		return result;
	}
		
	private final boolean
	NeedUVcalcMain( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		boolean  needUVcalc;
		
		needUVcalc = NeedUVcalc0();
		if( needUVcalc == true )
			return true;
		
		needUVcalc = NeedUVcalc1();
		if( needUVcalc == true )
			return true;
		
		return false;
	}

	private final boolean
	NeedUVcalc0()
	{
		NewNeedUVcalc0();
		NeedUVcalc0Main();
		
		return ChkNeedUVcalc0();
	}
	
	private final void
	NewNeedUVcalc0()
	{
		int  numUVspace = SrcUVspace().numUVspace;
		Gbl().gsUVcnt = new int[ numUVspace ];
		for( int i=0; i<numUVspace; i++ )
			Gbl().gsUVcnt[ i ] = 0;
	}
	
	private final void
	NeedUVcalc0Main()
	{
		int  num = SrcUVspace().gsUVseq.length;
		for( int i=0; i<num; i++ ) {
			int  uvSpaceNo = SrcUVspace().gsUVseq[ i ].uvSpaceNo;
			Gbl().gsUVcnt[ uvSpaceNo ]++;
		}
	}

	private final boolean
	ChkNeedUVcalc0()
	{
		int  num = Gbl().gsUVcnt.length;
		for( int i=0; i<num; i++ ) {
			if( Gbl().gsUVcnt[ i ] != 1 )
				return true;
		}
		
		return false;
	}
	
	private final boolean
	NeedUVcalc1() throws lvThrowable
	{
		NewNeedUVcalc1();
		NewVtxNumUV();
		NeedUVcalc1Main();
		
		return ChkNeedUVcalc1();
	}

	private final void
	NewNeedUVcalc1()
	{
		int  numFaceHalfSeq = Polygon().faceHalfSeq.length;
		Gbl().gsVtxUV = new int[ numFaceHalfSeq ];
		for( int i=0; i<numFaceHalfSeq; i++ )
			Gbl().gsVtxUV[ i ] = 0;
	}
	
	private final void
	NewVtxNumUV()
	{
		int  numVtxNumUV = SrcUVspace().vtxNumUV.length;
		Gbl().vtxNumUV = new lvRec.SeqPart[ numVtxNumUV ];
		for( int i=0; i<numVtxNumUV; i++ )
			Gbl().vtxNumUV[ i ] = new lvRec.SeqPart();
		
		for( int i=0; i<numVtxNumUV; i++ )
			Gbl().vtxNumUV[ i ].num = SrcUVspace().vtxNumUV[ i ];
			
		int  cnt = 0;
		for( int i=0; i<numVtxNumUV; i++ ) {
			Gbl().vtxNumUV[ i ].start = cnt;
			cnt += Gbl().vtxNumUV[ i ].num;
		}
	}
	
	private final void
	NeedUVcalc1Main()
	{
		int  start = 0;
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			int  numUVspace = SrcUVspace().gsNumUV[ i ];
			for( int j=0; j<numUVspace; j++ ) {
				int  uvSpaceNo = SrcUVspace().gsUVseq[ start + j ].uvSpaceNo;
				NeedUVcalc1One( i, uvSpaceNo );
			}
			start += numUVspace;
		}
	}
			
	private final void
	NeedUVcalc1One( int gsNo, int uvSpaceNo )
	{
		lvRec.SeqPart  face = Polygon().face[ gsNo ];
		for( int i=0; i<face.num; i++ ) {
			int  vtxNo = Polygon().faceHalfSeq[ face.start + i ].vtxNo;
			lvRec.SeqPart  vtxNumUV = Gbl().vtxNumUV[ vtxNo ];
			
			boolean  chk = false;
			for( int j=0; j<vtxNumUV.num; j++ ) {
				if( SrcUVspace().vtxUVseq[ vtxNumUV.start + j ].uvSpaceNo == uvSpaceNo ) {
					chk = true;
					break;
				}
			}
			
			if( chk == true )
				Gbl().gsVtxUV[ face.start + i ]++;
		}
	}
	
	private final boolean
	ChkNeedUVcalc1() throws lvThrowable
	{
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			int  numUVspace = SrcUVspace().gsNumUV[ i ];
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				Err().Assert( ( numUVspace >= Gbl().gsVtxUV[ face.start + j ] ), "lvCheckUVspace.ChkNeedUVcalc1(0)" );
				if( numUVspace > Gbl().gsVtxUV[ face.start + j ] )
					return true;
			}
		}
		
		return false;
	}
	
	private final void
	Finish()
	{
		Gbl().srcUVspace = null;
		Gbl().gsUVcnt    = null;
		Gbl().gsVtxUV    = null;
		Gbl().vtxNumUV   = null;
	}
	
}
