// **********************************************************************
//   EXECUTIVE SUMMARY
// Module Name: Vec3f.java
// Description: Definition of the Vec3f class
// Author: Kent A. Watsen, http://www.mbay.net/~watsen
// **********************************************************************


// **********************************************************************
//   IMPORTS
// **********************************************************************


// Standard classes
import java.lang.Math;

// Local classes
//import Matrix3f;


// **********************************************************************
//   CLASS
// **********************************************************************


class Vec3f
  {
  private
    float v[];

  public Vec3f()
    {
    v = new float[3];
    makeNull();
    }

  public Vec3f(float v0, float v1, float v2)
    {
    v = new float[3];
    set(v0, v1, v2);
    }

  public Vec3f(float vec[])
    {
    v = new float[3];
    set(vec);
    }

  public Vec3f(Vec3f vec)
    {
    v = new float[3];
    set(vec);
    }

  public void print()
    {
    System.out.println("v = " + v[0] + ", " + v[1] + ", " + v[2]);
    }

  public void set(float v0, float v1, float v2)
    {
    v[0] = v0;
    v[1] = v1;
    v[2] = v2;
    }

  public void get(float v0[], float v1[], float v2[])
    {
    v0[0] = v[0];
    v1[0] = v[1];
    v2[0] = v[2];
    }

  public void set(int index, float val)
    {
    v[index] = val;
    }

  public float get(int index)
    {
    return v[index];
    }

  public void set(float vec[])
    {
    v[0] = vec[0];
    v[1] = vec[1];
    v[2] = vec[2];
    }

  public void get(float vec[])
    {
    vec[0] = v[0];
    vec[1] = v[1];
    vec[2] = v[2];
    }

  public void set(Vec3f vec)
    {
    v[0] = vec.get(0);
    v[1] = vec.get(1);
    v[2] = vec.get(2);
    }

  public void get(Vec3f vec)
    {
    vec.set(0,v[0]);
    vec.set(1,v[1]);
    vec.set(2,v[2]);
    }

  public void makeNull()
    {
    v[0] = 0f;
    v[1] = 0f;
    v[2] = 0f;
    }

  public void negate()
    {
    v[0] = -v[0];
    v[1] = -v[1];
    v[2] = -v[2];
    }

  public void negate(Vec3f vec)
    {
    v[0] = -vec.get(0);
    v[1] = -vec.get(1);
    v[2] = -vec.get(2);
    }

  public void add(Vec3f vec)
    {
    v[0] = v[0] + vec.get(0);
    v[1] = v[1] + vec.get(1);
    v[2] = v[2] + vec.get(2);
    }

  public void add(Vec3f vec1, Vec3f vec2)
    {
    v[0] = vec1.get(0) + vec2.get(0);
    v[1] = vec1.get(1) + vec2.get(1);
    v[2] = vec1.get(2) + vec2.get(2);
    }

  public void sub(Vec3f vec)
    {
    v[0] = v[0] - vec.get(0);
    v[1] = v[1] - vec.get(1);
    v[2] = v[2] - vec.get(2);
    }

  public void sub(Vec3f vec1, Vec3f vec2)
    {
    v[0] = vec1.get(0) - vec2.get(0);
    v[1] = vec1.get(1) - vec2.get(1);
    v[2] = vec1.get(2) - vec2.get(2);
    }

  public void scale(float s)
    {
    v[0] = s * v[0];
    v[1] = s * v[1];
    v[2] = s * v[2];
    }

  public void scale(float s,  Vec3f vec)
    {
    v[0] = s * vec.get(0);
    v[1] = s * vec.get(1);
    v[2] = s * vec.get(2);
    }

  public float length()
    {
    return (float)Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
    }

  public float length_sqr()
    {
    return v[0]*v[0] + v[1]*v[1] + v[2]*v[2];
    }

  public void normalize()
    {
    float len_sqr;
    float one_over_length;

    len_sqr = length_sqr();
    if (len_sqr > 0.0001f)
      {
      one_over_length = 1.0f/(float)Math.sqrt(len_sqr);
      }
    else
      {
      one_over_length = 0.0f;
      }
    v[0] = v[0] * one_over_length;
    v[1] = v[1] * one_over_length;
    v[2] = v[2] * one_over_length;
    }

  public void normalize(Vec3f vec)
    {
    float len_sqr;
    float one_over_length;

    len_sqr = vec.length_sqr();
    if (len_sqr > 0.0001f)
      {
      one_over_length = 1.0f/(float)Math.sqrt(len_sqr);
      }
    else
      {
      one_over_length = 0.0f;
      }
    v[0] = vec.get(0) * one_over_length;
    v[1] = vec.get(1) * one_over_length;
    v[2] = vec.get(2) * one_over_length;
    }

  public float dot(Vec3f vec)
    {
    return v[0]*vec.get(0) + v[1]*vec.get(1) + v[2]*vec.get(2);
    }

  static public float dot(Vec3f vec1, Vec3f vec2)
    {
    return vec1.get(0)*vec2.get(0) + vec1.get(1)*vec2.get(1) + vec1.get(2)*vec2.get(2);
    }

  public void cross(Vec3f vec)
    {
    float tmp_float[] = new float[3];
    tmp_float[0] = v[1]*vec.get(2) - v[2]*vec.get(1);
    tmp_float[1] = v[2]*vec.get(0) - v[0]*vec.get(2);
    tmp_float[2] = v[0]*vec.get(1) - v[1]*vec.get(0);
    set(tmp_float);
    }

  public void cross(Vec3f vec1, Vec3f vec2)
    {
    v[0] = vec1.get(1)*vec2.get(2) - vec1.get(2)*vec2.get(1);
    v[1] = vec1.get(2)*vec2.get(0) - vec1.get(0)*vec2.get(2);
    v[2] = vec1.get(0)*vec2.get(1) - vec1.get(1)*vec2.get(0);
    }

  public void xform(Matrix3f mat) // math_utils
    {
    float tmp_v[] = new float[3];
    float m[][] = new float[3][3];

    mat.getMat(m);
    tmp_v[0] = v[0]*m[0][0] + v[1]*m[0][1] + v[2]*m[0][2];
    tmp_v[1] = v[0]*m[1][0] + v[1]*m[1][1] + v[2]*m[1][2];
    tmp_v[2] = v[0]*m[2][0] + v[1]*m[2][1] + v[2]*m[2][2];
    set(tmp_v);
    }

  public void xform(Matrix3f mat, Vec3f vec) // math_utils
    {
    float tmp_v[] = new float[3];
    float m[][] = new float[3][3];

    vec.get(tmp_v);
    mat.getMat(m);
    v[0] = tmp_v[0]*m[0][0] + tmp_v[1]*m[0][1] + tmp_v[2]*m[0][2];
    v[1] = tmp_v[0]*m[1][0] + tmp_v[1]*m[1][1] + tmp_v[2]*m[1][2];
    v[2] = tmp_v[0]*m[2][0] + tmp_v[1]*m[2][1] + tmp_v[2]*m[2][2];
    }
}
