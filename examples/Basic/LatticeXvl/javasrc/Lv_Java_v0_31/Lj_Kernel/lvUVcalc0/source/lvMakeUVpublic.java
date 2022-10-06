//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVpublic.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/07-)
 * 
 */
public class lvMakeUVpublic extends lvRoot {

	/**
	 * ���_�ɑ΂���ꎞ�I�����\����
	 */
	private static class TmpVertex {

		/** �����_�ɕt������UV��Ԃ̐�						*/
		private int      numUVpublic		= 0;
		
		/** UV��ԏ��i�L���z�񐔂́AnumUVpublic �j		*/
		private int      uvPublicNo[]		= null;


		private boolean  hasUVpublicNo;

		/** �ꎞ�IUV��Ԑ����p���񃊃X�g�i�Е����j			*/
		private int      nextVtxNo;

		/** JoinUVspaceList()�p�̈ꎞ�IUV���				*/
		private int      tmpUVspaceNo;

		/** JoinUVspaceList()�p�̈ꎞ�IUV��ԃJ�E���^		*/
		private int      tmpUVspaceCnt;
		
		/** ����O��UV��ԓ��́u���[�J��UV���No.�v			*/
		public int       localUVspaceNo;
	}

	/**
	 * �u�O��UV��ԓ���1�̓���UV��ԁv�ɑ΂���ꎞ�I�����\����
	 */
	private static class TmpUVpublicOne {

		/** ���_No.�̔z��								*/
		private int  vtxNo[]				= null;
	}
	
	/**
	 * �O��UV��Ԃɑ΂���ꎞ�I�����\����
	 */
	private static class TmpUVpublic {

		/** ����UV��ԏ��i���̊O��UV��ԓ��̓���UV��Ԃ̐��������݂��� �j	*/
		private TmpUVpublicOne  uvSpace[]	= null;
	}

// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No						*/
		private int                         curShellNo;

		/** �J�����glvToKernelUV�f�[�^				*/
		private lvToKernelUV                srcUVspace			= null;

		/** �o�̓f�[�^�̑O�f�[�^					*/
		private lvMakeUVspaceType.UVpublic  preDstUVpublic		= new lvMakeUVspaceType.UVpublic();
		
		/** �o�̓f�[�^								*/
		private lvMakeUVspaceType.UVpublic  dstUVpublic			= null;
		
		/** ���_�ɑ΂���ꎞ���					*/
		private TmpVertex                   vertex[]			= null;

		/** �O��UV��Ԃɑ΂���ꎞ���				*/
		private TmpUVpublic                 uvPublic[]			= null;

		/** �O��UV��ԁi�e�N�X�`���p�j�̈ꎞ���	*/
		private lvUVpublic                  tmpShlUVpublic		= new lvUVpublic();

		/** lvMakeUVpubCorrect�I�u�W�F�N�g			*/
		private lvMakeUVpubCorrect          makeUVpubCorrect	= null;
		
		private lvMakeUVpubCorrect.DstInfo  correct				= new lvMakeUVpubCorrect.DstInfo();
		
		private int                         vtxNumUVstart[]		= null;
		
		private int                         uvSpaceVtx[]		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Global( lvGlobal dt )
		{
			makeUVpubCorrect = new lvMakeUVpubCorrect( dt );
		}
	}


	/** ���N���X�p�̃O���[�o���f�[�^						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVpublic;
	}
	/** lvToKernelUV�f�[�^�p�N���X�I�u�W�F�N�g				*/
	private final lvToKernelUV
	SrcUVspace()
	{
		return  Gbl().srcUVspace;
	}
	/** lvMakeUVspaceType.UVpublic�f�[�^�p�N���X�I�u�W�F�N�g	*/
	private final lvMakeUVspaceType.UVpublic
	PreDstUVpublic()
	{
		return  Gbl().preDstUVpublic;
	}
	/**  */
	private final lvMakeUVspaceType.UVpublic
	DstUVpublic()
	{
		return  Gbl().dstUVpublic;
	}
	/** lvUVpublic�f�[�^�p�N���X�I�u�W�F�N�g�i�ꎞ�g�p�j		*/
	private final lvUVpublic
	TmpShlUVpublic()
	{
		return  Gbl().tmpShlUVpublic;
	}
	/**  */
	private final lvMakeUVpubCorrect.DstInfo
	Correct()
	{
		return  Gbl().correct;
	}
	/** lvPolygon�f�[�^�p�N���X�I�u�W�F�N�g						*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvUVpublic�f�[�^�p�N���X�I�u�W�F�N�g				*/
	private final lvUVpublic
	ShellUVpublic()
	{
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvPublic;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lvMakeUVpublic( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public lvMakeUVspaceType.UVpublic
	Exec( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		if( srcUVspace == null )
			return null;
			
		Gbl().curShellNo  = shellNo;
		Gbl().srcUVspace  = srcUVspace;
		Gbl().dstUVpublic = new lvMakeUVspaceType.UVpublic();
		
		GsProc0();
		VtxProc0();
		
		ExecMain();
		
		UVpublicProc0();
		VtxProc1();
		GsProc1();
		
		Gbl().makeUVpubCorrect.Exec( shellNo, Gbl().preDstUVpublic, Gbl().tmpShlUVpublic, Gbl().correct );
		
		UVpublicProc1();
		VtxProc2();
		GsProc2();
		
		Finish();
		
		return Gbl().dstUVpublic;
	}

	private final void
	GsProc0()
	{
		SetGNumUV();
	}
	
	private final void
	SetGNumUV()
	{
		int  num = SrcUVspace().gsNumUV.length;
		PreDstUVpublic().gsNumUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			PreDstUVpublic().gsNumUV[ i ] = new lvRec.SeqPart();
			
		int  cnt = 0;
		for( int i=0; i<num; i++ ) {
			PreDstUVpublic().gsNumUV[ i ].start = cnt;
			PreDstUVpublic().gsNumUV[ i ].num   = SrcUVspace().gsNumUV[ i ];
			
			cnt += PreDstUVpublic().gsNumUV[ i ].num;
		}
	}
	
	private final void
	VtxProc0()
	{
		NewTmpVertex();
		CalcNumInfoTmp();
		VtxProc0Main();
	}
	
	private final void
	NewTmpVertex()
	{
		int  num = Polygon().vertex.length;
		Gbl().vertex = new TmpVertex[ num ];
		for( int i=0; i<num; i++ )
			Gbl().vertex[ i ] = new TmpVertex();
	}

	private final void
	CalcNumInfoTmp()
	{
		int  numVtx = Polygon().vertex.length;
		for( int i=0; i<numVtx; i++ )
			Gbl().vertex[ i ].numUVpublic = 0;
			
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			int  numUVspace = PreDstUVpublic().gsNumUV[ i ].num;
			int  numHalf    = Polygon().face[ i ].num;
			for( int j=0; j<numHalf; j++ ) {
				int  vtxNo = Polygon().faceHalfSeq[ Polygon().face[ i ].start + j ].vtxNo;
				Gbl().vertex[ vtxNo ].numUVpublic += numUVspace;
			}
		}
		
		for( int i=0; i<numVtx; i++ ) {
			int  numUVpublic = Gbl().vertex[ i ].numUVpublic;
			Gbl().vertex[ i ].uvPublicNo = new int[ numUVpublic ];
		}
	}
	
	private final void
	VtxProc0Main()
	{
		int  numVtx = Polygon().vertex.length;
		for( int i=0; i<numVtx; i++ )
			Gbl().vertex[ i ].numUVpublic = 0;
			
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			int  numHalf = Polygon().face[ i ].num;
			for( int j=0; j<numHalf; j++ )
				VtxProc0One( i, j );
		}
	}
	
	private final void
	VtxProc0One( int gsNo, int halfNo )
	{
		lvRec.SeqPart  gsNumUV = PreDstUVpublic().gsNumUV[ gsNo ];
		
		int  vtxNo = Polygon().faceHalfSeq[ Polygon().face[ gsNo ].start + halfNo ].vtxNo;
		
		for( int i=0; i<gsNumUV.num; i++ ) {
			boolean  exist        = false;
			int      numUVpublic      = Gbl().vertex[ vtxNo ].numUVpublic;
			int      uvPublicNoGs = SrcUVspace().gsUVseq[ gsNumUV.start + i ].uvSpaceNo;
			for( int j=0; j<numUVpublic; j++ ) {
				if( uvPublicNoGs == Gbl().vertex[ vtxNo ].uvPublicNo[ j ] ) {
					exist = true;
					break;
				}
			}
			if( exist == false ) {
				Gbl().vertex[ vtxNo ].uvPublicNo[ numUVpublic ] = uvPublicNoGs;
				Gbl().vertex[ vtxNo ].numUVpublic++;
			}
		}
	}
	
	private final void
	ExecMain() throws lvThrowable
	{
		int  numUVpublic = SrcUVspace().numUVspace;
		NewTmpUVpublic( numUVpublic );
		for( int i=0; i<numUVpublic; i++ )
			ExecOne( i );
	}
	
	private final void
	NewTmpUVpublic( int numUVpublic )
	{
		Gbl().uvPublic = new TmpUVpublic[ numUVpublic ];
		for( int i=0; i<numUVpublic; i++ )
			Gbl().uvPublic[ i ] = new TmpUVpublic();
	}

	private final void
	ExecOne( int uvPublicNo ) throws lvThrowable
	{
		InitUVspaceList( uvPublicNo );
		JoinUVspaceList( uvPublicNo );
		
		int  numUVspace = SetUVspaceNo();
		NewTmpUVpublicOne( uvPublicNo, numUVspace );
		
		SetTmpUVpublicOne( uvPublicNo );
	}
	
	private final void
	InitUVspaceList( int uvPublicNo )
	{
		int  numVtx = Polygon().vertex.length;
		for( int i=0; i<numVtx; i++ ) {
			Gbl().vertex[ i ].hasUVpublicNo = false;
			
			boolean  exist = false;
			for( int j=0; j<Gbl().vertex[ i ].numUVpublic; j++ ) {
				if( Gbl().vertex[ i ].uvPublicNo[ j ] == uvPublicNo ) {
					exist = true;
					break;
				}
			}
			if( exist == true ) {
				Gbl().vertex[ i ].hasUVpublicNo = true;
				Gbl().vertex[ i ].nextVtxNo     = i;
				Gbl().vertex[ i ].tmpUVspaceNo  = i;
				Gbl().vertex[ i ].tmpUVspaceCnt = 1;
			}
		}
	}
	
	private final void
	JoinUVspaceList( int uvPublicNo )
	{
		int  i, num, vtxNo0, vtxNo1, tmpUVspaceNo0, tmpUVspaceNo1, vtxNo, newNo, numLoop, vtxNo0Tmp, vtxNo1Tmp;

		num = Polygon().edge.length;
		for( i=0; i<num; i++ ) {
			lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ i ].face[ 0 ];
			lvRec.SeqPart           face     = Polygon().face[ faceHalf.faceNo ];
			vtxNo0 = Polygon().faceHalfSeq[ face.start + faceHalf.halfNo ].vtxNo;
			vtxNo1 = Polygon().faceHalfSeq[ face.start + ( ( faceHalf.halfNo + 1 ) % face.num ) ].vtxNo;
			if( Gbl().vertex[ vtxNo0 ].hasUVpublicNo == false || Gbl().vertex[ vtxNo1 ].hasUVpublicNo == false )
				continue;

			tmpUVspaceNo0 = Gbl().vertex[ vtxNo0 ].tmpUVspaceNo;
			tmpUVspaceNo1 = Gbl().vertex[ vtxNo1 ].tmpUVspaceNo;
			if( tmpUVspaceNo0 == tmpUVspaceNo1 )
				continue;

			if( Gbl().vertex[ tmpUVspaceNo0 ].tmpUVspaceCnt >= Gbl().vertex[ tmpUVspaceNo1 ].tmpUVspaceCnt ) {
				newNo   = tmpUVspaceNo0;
				vtxNo   = vtxNo1;
				numLoop = Gbl().vertex[ tmpUVspaceNo1 ].tmpUVspaceCnt;
				Gbl().vertex[ tmpUVspaceNo0 ].tmpUVspaceCnt += Gbl().vertex[ tmpUVspaceNo1 ].tmpUVspaceCnt;
				Gbl().vertex[ tmpUVspaceNo1 ].tmpUVspaceCnt  = 0;
			}
			else {
				newNo   = tmpUVspaceNo1;
				vtxNo   = vtxNo0;
				numLoop = Gbl().vertex[ tmpUVspaceNo0 ].tmpUVspaceCnt;
				Gbl().vertex[ tmpUVspaceNo1 ].tmpUVspaceCnt += Gbl().vertex[ tmpUVspaceNo0 ].tmpUVspaceCnt;
				Gbl().vertex[ tmpUVspaceNo0 ].tmpUVspaceCnt  = 0;
			}

			for( int j=0; j<numLoop; j++ ) {
				Gbl().vertex[ vtxNo ].tmpUVspaceNo = newNo;
				vtxNo = Gbl().vertex[ vtxNo ].nextVtxNo;
			}

			vtxNo0Tmp = Gbl().vertex[ vtxNo0 ].nextVtxNo;
			vtxNo1Tmp = Gbl().vertex[ vtxNo1 ].nextVtxNo;
			Gbl().vertex[ vtxNo0 ].nextVtxNo = vtxNo1Tmp;
			Gbl().vertex[ vtxNo1 ].nextVtxNo = vtxNo0Tmp;
		}
	}

	private final int
	SetUVspaceNo() throws lvThrowable
	{
		int  i, vtxNo, tmpUVspaceNo, numLoop;
		int  numVtx = Polygon().vertex.length;

		for( i=0; i<numVtx; i++ )
			Gbl().vertex[ i ].localUVspaceNo = -1;

		int  cnt = 0;
		for( i=0; i<numVtx; i++ ) {
			if( Gbl().vertex[ i ].hasUVpublicNo == false )
				continue;
			else if( Gbl().vertex[ i ].localUVspaceNo >= 0 )
				continue;

			vtxNo = i;
			tmpUVspaceNo = Gbl().vertex[ i ].tmpUVspaceNo;
			numLoop      = Gbl().vertex[ tmpUVspaceNo ].tmpUVspaceCnt;
			for( int j=0; j<numLoop; j++ ) {
				Err().Assert( ( Gbl().vertex[ vtxNo ].localUVspaceNo < 0    ), "lvMakeUVpublic.SetUVspaceNo(0)" );
				Err().Assert( ( Gbl().vertex[ vtxNo ].hasUVpublicNo == true ), "lvMakeUVpublic.SetUVspaceNo(1)" );
				Gbl().vertex[ vtxNo ].localUVspaceNo = cnt;
				vtxNo = Gbl().vertex[ vtxNo ].nextVtxNo;
			}
			cnt++;
		}

		return  cnt;
	}

	private final void
	NewTmpUVpublicOne( int uvPublicNo, int numUVspace )
	{
		int  i, j, cnt;
		int  numVtx = Polygon().vertex.length;

		Gbl().uvPublic[ uvPublicNo ].uvSpace = new TmpUVpublicOne[ numUVspace ];
		for( i=0; i<numUVspace; i++ ) {
			Gbl().uvPublic[ uvPublicNo ].uvSpace[ i ] = new TmpUVpublicOne();
		
			cnt = 0;
			for( j=0; j<numVtx; j++ ) {
				if( Gbl().vertex[ j ].hasUVpublicNo == false )
					continue;
				
				if( Gbl().vertex[ j ].localUVspaceNo == i )
					cnt++;
			}
			
			Gbl().uvPublic[ uvPublicNo ].uvSpace[ i ].vtxNo = new int[ cnt ];
		}
	}
	
	private final void
	SetTmpUVpublicOne( int uvPublicNo )
	{
		int  i, j, cnt;
		int  numVtx = Polygon().vertex.length;

		int  numUVspace = Gbl().uvPublic[ uvPublicNo ].uvSpace.length;
		for( i=0; i<numUVspace; i++ ) {
			cnt = 0;
			for( j=0; j<numVtx; j++ ) {
				if( Gbl().vertex[ j ].hasUVpublicNo == false )
					continue;
				
				if( Gbl().vertex[ j ].localUVspaceNo == i ) {
					Gbl().uvPublic[ uvPublicNo ].uvSpace[ i ].vtxNo[ cnt ] = j;
					cnt++;
				}
			}
		}
	}
	
	private final void
	UVpublicProc0()
	{
		UVpublicProc00();
		UVpublicProc01();
	}
	
	private final void
	UVpublicProc00()
	{
		int  numUVpublic = SrcUVspace().numUVspace;
		TmpShlUVpublic().uvPublic = new lvRec.SeqPart[ numUVpublic ];
		for( int i=0; i<numUVpublic; i++ )
			TmpShlUVpublic().uvPublic[ i ] = new lvRec.SeqPart();
			
		int  cnt = 0;
		for( int i=0; i<numUVpublic; i++ ) {
			TmpShlUVpublic().uvPublic[ i ].start = cnt;
			TmpShlUVpublic().uvPublic[ i ].num   = Gbl().uvPublic[ i ].uvSpace.length;
			
			cnt += TmpShlUVpublic().uvPublic[ i ].num;
		}
	}
	
	private final void
	UVpublicProc01()
	{
		int  numUVpublic = SrcUVspace().numUVspace;
		int  cnt = 0;
		for( int i=0; i<numUVpublic; i++ )
			cnt += TmpShlUVpublic().uvPublic[ i ].num;
			
		TmpShlUVpublic().uvSpace = new lvUVpublic.UVspace[ cnt ];
		for( int i=0; i<cnt; i++ )
			TmpShlUVpublic().uvSpace[ i ] = new lvUVpublic.UVspace();
			
		cnt = 0;
		for( int i=0; i<numUVpublic; i++ ) {
			int  num = TmpShlUVpublic().uvPublic[ i ].num;
			for( int j=0; j<num; j++ ) {
				TmpShlUVpublic().uvSpace[ cnt ].uvPublicNo = i;
				cnt++;
			}
		}
	}
	
	private final void
	VtxProc1()
	{
		VtxProc10();
		VtxProc11();
	}
	
	private final void
	VtxProc10()
	{
		int  cnt;
		
		int  numVtx = Polygon().vertex.length;
		PreDstUVpublic().vtxUV = new lvRec.SeqPart[ numVtx ];
		for( int i=0; i<numVtx; i++ ) {
			PreDstUVpublic().vtxUV[ i ] = new lvRec.SeqPart();
			PreDstUVpublic().vtxUV[ i ].num = 0;
		}

		int  numUVpublic = SrcUVspace().numUVspace;
		for( int i=0; i<numUVpublic; i++ ) {			
			int  numUVspace = Gbl().uvPublic[ i ].uvSpace.length;
			for( int j=0; j<numUVspace; j++ ) {
				int  numVtxLocal = Gbl().uvPublic[ i ].uvSpace[ j ].vtxNo.length;
				for( int k=0; k<numVtxLocal; k++ ) {
					int  vtxNo = Gbl().uvPublic[ i ].uvSpace[ j ].vtxNo[ k ];
					PreDstUVpublic().vtxUV[ vtxNo ].num++;
				}
			}
		}
		
		cnt = 0;
		for( int i=0; i<numVtx; i++ ) {
			PreDstUVpublic().vtxUV[ i ].start = cnt;
			cnt += PreDstUVpublic().vtxUV[ i ].num;
		}
	}
	
	private final void
	VtxProc11()
	{
		int  numVtx = Polygon().vertex.length;

		int  cnt = 0;
		for( int i=0; i<numVtx; i++ )
			cnt += PreDstUVpublic().vtxUV[ i ].num;
		
		PreDstUVpublic().vtxUVseq = new lvMakeUVspaceType.UVpublicVtx[ cnt ];
		for( int i=0; i<cnt; i++ )
			PreDstUVpublic().vtxUVseq[ i ] = new lvMakeUVspaceType.UVpublicVtx();
		
		for( int i=0; i<numVtx; i++ )
			PreDstUVpublic().vtxUV[ i ].num = 0;

		int  numUVpublic = TmpShlUVpublic().uvPublic.length;
		for( int i=0; i<numUVpublic; i++ ) {			
			lvRec.SeqPart  uvPublic = TmpShlUVpublic().uvPublic[ i ];
			for( int j=0; j<uvPublic.num; j++ ) {
				int  numVtxLocal = Gbl().uvPublic[ i ].uvSpace[ j ].vtxNo.length;
				int  uvSpaceNo   = uvPublic.start + j;
				for( int k=0; k<numVtxLocal; k++ ) {
					int  vtxNo = Gbl().uvPublic[ i ].uvSpace[ j ].vtxNo[ k ];
					
					lvRec.SeqPart vtxUV = PreDstUVpublic().vtxUV[ vtxNo ];
					PreDstUVpublic().vtxUVseq[ vtxUV.start + vtxUV.num ].uvSpaceNo = uvSpaceNo;
					
					PreDstUVpublic().vtxUV[ vtxNo ].num++;
				}
			}
		}
	}
	
	private final void
	GsProc1() throws lvThrowable
	{
		GsProc10();
		GsProc11();
	}
	
	private final void
	GsProc10()
	{
		int  num = SrcUVspace().gsNumUV.length;
		int  cnt = 0;
		for( int i=0; i<num; i++ )
			cnt += PreDstUVpublic().gsNumUV[ i ].num;
			
		PreDstUVpublic().gsUVseq = new int[ cnt ];
	}
	
	private final void
	GsProc11() throws lvThrowable
	{
		int  numGs = SrcUVspace().gsNumUV.length;
		for( int i=0; i< numGs; i++ ) {
			lvRec.SeqPart  gsNumUV = PreDstUVpublic().gsNumUV[ i ];
			for( int j=0; j<gsNumUV.num; j++ ) {
				int  gsUVseqIndex = gsNumUV.start + j;
				int  uvPublicNo   = SrcUVspace().gsUVseq[ gsUVseqIndex ].uvSpaceNo;
				int  numHalf      = Polygon().face[ i ].num;
				for( int k=0; k<numHalf; k++ )
					GsProc11One( i, k, uvPublicNo, gsUVseqIndex );
			}
		}
	}
	
	private final void
	GsProc11One( int gsNo, int halfNo, int uvPublicNo0, int gsUVseqIndex ) throws lvThrowable
	{
		int  vtxNo = Polygon().faceHalfSeq[ Polygon().face[ gsNo ].start + halfNo ].vtxNo;
		lvRec.SeqPart  vtxUV = PreDstUVpublic().vtxUV[ vtxNo ];
		
		int      uvSpaceNo = -1;
		boolean  chk = false;
		for( int i=0; i<vtxUV.num; i++ ) {
			uvSpaceNo = PreDstUVpublic().vtxUVseq[ vtxUV.start + i ].uvSpaceNo;
			int  uvPublicNo = TmpShlUVpublic().uvSpace[ uvSpaceNo ].uvPublicNo;
			if( uvPublicNo == uvPublicNo0 ) {
				chk = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVpublic.GsProc11One(0)" );
		
		PreDstUVpublic().gsUVseq[ gsUVseqIndex ] = uvSpaceNo;
	}
	
	private final void
	UVpublicProc1()
	{
		UVpublicProc10();
		UVpublicProc11();
	}
	
	private final void
	UVpublicProc10()
	{
		int  numUVpublic = TmpShlUVpublic().uvPublic.length;
		ShellUVpublic().uvPublic = new lvRec.SeqPart[ numUVpublic ];
		for( int i=0; i<numUVpublic; i++ )
			ShellUVpublic().uvPublic[ i ] = new lvRec.SeqPart();

		for( int i=0; i<numUVpublic; i++ ) {
			lvRec.SeqPart  uvPublic = TmpShlUVpublic().uvPublic[ i ];
			int  numNonBnd = 0;
			for( int j=0; j<uvPublic.num; j++ ) {
				if( Correct().hasBound[ uvPublic.start + j ] == false )
					numNonBnd++;
			}
			ShellUVpublic().uvPublic[ i ].num = uvPublic.num + numNonBnd;
		}
			
		int  cnt = 0;
		for( int i=0; i<numUVpublic; i++ ) {
			ShellUVpublic().uvPublic[ i ].start = cnt;
			cnt += ShellUVpublic().uvPublic[ i ].num;
		}
	}
	
	private final void
	UVpublicProc11()
	{
		int  numUVpublic = TmpShlUVpublic().uvPublic.length;
		int  cnt = 0;
		for( int i=0; i<numUVpublic; i++ )
			cnt += ShellUVpublic().uvPublic[ i ].num;
			
		ShellUVpublic().uvSpace = new lvUVpublic.UVspace[ cnt ];
		for( int i=0; i<cnt; i++ )
			ShellUVpublic().uvSpace[ i ] = new lvUVpublic.UVspace();
			
		cnt = 0;
		for( int i=0; i<numUVpublic; i++ ) {
			int  num = ShellUVpublic().uvPublic[ i ].num;
			for( int j=0; j<num; j++ ) {
				ShellUVpublic().uvSpace[ cnt ].uvPublicNo = i;
				cnt++;
			}
		}
	}
		
	private final void
	VtxProc2() throws lvThrowable
	{
		MakeVtxNumUVstart();
		
		VtxProc20();
		VtxProc21();
	}
	
	private final void
	MakeVtxNumUVstart()
	{
		int  num = SrcUVspace().vtxNumUV.length;
		Gbl().vtxNumUVstart = new int[ num ];
		
		int  cnt = 0;
		for( int i=0; i<num; i++ ) {
			Gbl().vtxNumUVstart[ i ] = cnt;
			cnt += SrcUVspace().vtxNumUV[ i ];
		}
	}
	
	private final void
	VtxProc20()
	{
		int  cnt;
		
		int  numVtx = Polygon().vertex.length;
		DstUVpublic().vtxUV = new lvRec.SeqPart[ numVtx ];
		for( int i=0; i<numVtx; i++ ) {
			DstUVpublic().vtxUV[ i ] = new lvRec.SeqPart();
			DstUVpublic().vtxUV[ i ].num = PreDstUVpublic().vtxUV[ i ].num;
		}

		int  numUVpublic = TmpShlUVpublic().uvPublic.length;
		for( int i=0; i<numUVpublic; i++ ) {
			lvRec.SeqPart  srcUVpublic = TmpShlUVpublic().uvPublic[ i ];
			for( int j=0; j<srcUVpublic.num; j++ ) {
				if( Correct().hasBound[ srcUVpublic.start + j ] == false )
					VtxProc20NonBnd();
			}
		}
		
		cnt = 0;
		for( int i=0; i<numVtx; i++ ) {
			DstUVpublic().vtxUV[ i ].start = cnt;
			cnt += DstUVpublic().vtxUV[ i ].num;
		}
	}
	
	private final void
	VtxProc20NonBnd()
	{
		lvRec.SeqPart  face = Polygon().face[ Correct().selGsNoForNonBnd ];
		for( int i=0; i<face.num; i++ ) {
			int  vtxNo = Polygon().faceHalfSeq[ face.start + i ].vtxNo;
			DstUVpublic().vtxUV[ vtxNo ].num++;
		}
	}
	
	private final void
	VtxProc21() throws lvThrowable
	{
		int  numVtx = Polygon().vertex.length;

		int  cnt = 0;
		for( int i=0; i<numVtx; i++ )
			cnt += DstUVpublic().vtxUV[ i ].num;
		
		DstUVpublic().vtxUVseq = new lvMakeUVspaceType.UVpublicVtx[ cnt ];
		for( int i=0; i<cnt; i++ )
			DstUVpublic().vtxUVseq[ i ] = new lvMakeUVspaceType.UVpublicVtx();
		
		for( int i=0; i<numVtx; i++ )
			DstUVpublic().vtxUV[ i ].num = 0;

		int  numUVpublic = TmpShlUVpublic().uvPublic.length;
		for( int i=0; i<numUVpublic; i++ ) {
			lvRec.SeqPart  srcUVpublic = TmpShlUVpublic().uvPublic[ i ];
			lvRec.SeqPart  dstUVpublic = ShellUVpublic().uvPublic[ i ];
			int  num = 0;
			for( int j=0; j<srcUVpublic.num; j++ ) {
				int  dstUVspaceNo = dstUVpublic.start + num;
				
				VtxProc21Main( i, j, dstUVspaceNo );
				num++;
				
				if( Correct().hasBound[ srcUVpublic.start + j ] == false ) {
					VtxProc21NonBnd( dstUVspaceNo + 1 );
					num++;
				}
			}
		}
	}
	
	private final void
	VtxProc21Main( int srcUVpublicNo, int srcUVSpaceOfs, int dstUVspaceNo ) throws lvThrowable
	{
		int  numVtxLocal = Gbl().uvPublic[ srcUVpublicNo ].uvSpace[ srcUVSpaceOfs ].vtxNo.length;
		for( int i=0; i<numVtxLocal; i++ ) {
			int  vtxNo = Gbl().uvPublic[ srcUVpublicNo ].uvSpace[ srcUVSpaceOfs ].vtxNo[ i ];
					
			lvRec.SeqPart vtxUV = DstUVpublic().vtxUV[ vtxNo ];
			DstUVpublic().vtxUVseq[ vtxUV.start + vtxUV.num ].uvSpaceNo = dstUVspaceNo;
			DstUVpublic().vtxUVseq[ vtxUV.start + vtxUV.num ].st        = GetST( vtxNo, dstUVspaceNo );
					
			DstUVpublic().vtxUV[ vtxNo ].num++;
		}
	}
	
	private final void
	VtxProc21NonBnd( int uvSpaceNo ) throws lvThrowable
	{
		lvRec.SeqPart  face = Polygon().face[ Correct().selGsNoForNonBnd ];
		for( int i=0; i<face.num; i++ ) {
			int  vtxNo = Polygon().faceHalfSeq[ face.start + i ].vtxNo;
			
			lvRec.SeqPart  vtxUV = DstUVpublic().vtxUV[ vtxNo ];
			DstUVpublic().vtxUVseq[ vtxUV.start + vtxUV.num ].uvSpaceNo = uvSpaceNo;
			DstUVpublic().vtxUVseq[ vtxUV.start + vtxUV.num ].st        = GetST( vtxNo, uvSpaceNo );
			
			DstUVpublic().vtxUV[ vtxNo ].num++;
		}
	}
	
	private final lvUVdt
	GetST( int vtxNo, int uvSpaceNo ) throws lvThrowable
	{
		int  vtxNumUV = SrcUVspace().vtxNumUV[ vtxNo ];
		if( vtxNumUV == 0 )
			return null;
		
		int  start = Gbl().vtxNumUVstart[ vtxNo ];
		int  uvPublicNo = ShellUVpublic().uvSpace[ uvSpaceNo ].uvPublicNo;
		
		lvUVdt   st    = null;
		boolean  exist = false;
		for( int i=0; i<vtxNumUV; i++ ) {
			int  srcUVpublicNo = SrcUVspace().vtxUVseq[ start + i ].uvSpaceNo;
			if( uvPublicNo == srcUVpublicNo ) {
				exist = true;
				
				st = new lvUVdt();
				lvUVdt.Copy( SrcUVspace().vtxUVseq[ start + i ].uv, st );
				
				break;
			}
		}
		Err().Assert( ( exist == true ), "lvMakeUVpublic.GetST(0)" );
		
		return st;
	}
	
	private final void
	GsProc2() throws lvThrowable
	{
		NewUVspaceVtx();
		GsProc20();
		GsProc21();
		GsProc22();
	}
	
	private final void
	NewUVspaceVtx()
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		Gbl().uvSpaceVtx = new int[ numUVspace ];
	}
	
	private final void
	GsProc20()
	{
		int  num = SrcUVspace().gsNumUV.length;
		DstUVpublic().gsNumUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			DstUVpublic().gsNumUV[ i ] = new lvRec.SeqPart();
			
		int  cnt = 0;
		for( int i=0; i<num; i++ ) {
			DstUVpublic().gsNumUV[ i ].start = cnt;
			DstUVpublic().gsNumUV[ i ].num   = SrcUVspace().gsNumUV[ i ];
			
			cnt += DstUVpublic().gsNumUV[ i ].num;
		}
	}
	
	private final void
	GsProc21()
	{
		int  num = SrcUVspace().gsNumUV.length;
		int  cnt = 0;
		for( int i=0; i<num; i++ )
			cnt += DstUVpublic().gsNumUV[ i ].num;
			
		DstUVpublic().gsUVseq = new int[ cnt ];
	}
	
	private final void
	GsProc22() throws lvThrowable
	{
		int  numGs = SrcUVspace().gsNumUV.length;
		for( int i=0; i< numGs; i++ )
			GsProc22One( i );
	}
	
	private final void
	GsProc22One( int gsNo ) throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ )
			Gbl().uvSpaceVtx[ i ] = 0;
		
		lvRec.SeqPart  gsNumUV = DstUVpublic().gsNumUV[ gsNo ];

		lvRec.SeqPart  face = Polygon().face[ gsNo ];
		for( int i=0; i<face.num; i++ ) {
			int  vtxNo = Polygon().faceHalfSeq[ face.start + i ].vtxNo;
			lvRec.SeqPart  vtxUV = DstUVpublic().vtxUV[ vtxNo ];
		
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNo = DstUVpublic().vtxUVseq[ vtxUV.start + j ].uvSpaceNo;
				Gbl().uvSpaceVtx[ uvSpaceNo ]++;
			}
		}
		
		int  cnt = 0;
		for( int i=0; i<numUVspace; i++ ) {
			if( Gbl().uvSpaceVtx[ i ] == face.num ) {
				int  uvPublicNo0 = ShellUVpublic().uvSpace[ i ].uvPublicNo;
				
				boolean  exist = false;
				for( int j=0; j<gsNumUV.num; j++ ) {
					int  uvPublicNo1 = SrcUVspace().gsUVseq[ gsNumUV.start + j ].uvSpaceNo;
					if( uvPublicNo0 == uvPublicNo1 ) {
						exist = true;
						break;
					}
				}
				
				if( exist == true ) {
					if( cnt == gsNumUV.num ) {
						if( gsNo == Correct().selGsNoForNonBnd )
							DstUVpublic().gsUVseq[ gsNumUV.start + ( cnt - 1 ) ] = i;
					}
					else {
						DstUVpublic().gsUVseq[ gsNumUV.start + cnt ] = i;
						cnt++;
					}
				}
			}
		}
		
		Err().Assert( ( cnt == gsNumUV.num ), "lvMakeUVpublic.GsProc22One(0)" );
	}

	private final void
	Finish()
	{
		Gbl().srcUVspace    = null;
		Gbl().vertex        = null;
		Gbl().uvPublic      = null;
		Gbl().vtxNumUVstart = null;
		Gbl().uvSpaceVtx    = null;
	}
	
}
