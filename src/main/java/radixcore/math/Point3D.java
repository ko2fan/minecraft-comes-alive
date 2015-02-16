/*******************************************************************************
 * Point3D.java
 * Copyright (c) 2014 WildBamaBoy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MCA Minecraft Mod license.
 ******************************************************************************/

package radixcore.math;

import java.util.List;

import radixcore.enums.EnumAxis;
import radixcore.helpers.MathHelper;

/**
 * Used to store a group of 3D coordinates and easily move them around.
 */
public final class Point3D
{
	public short sPosX;
	public short sPosY;
	public short sPosZ;

	public int iPosX;
	public int iPosY;
	public int iPosZ;

	public float fPosX;
	public float fPosY;
	public float fPosZ;

	public double dPosX;
	public double dPosY;
	public double dPosZ;

	public Point3D(short posX, short posY, short posZ)
	{
		sPosX = posX;
		sPosY = posY;
		sPosZ = posZ;

		iPosX = posX;
		iPosY = posY;
		iPosZ = posZ;

		fPosX = posX;
		fPosY = posY;
		fPosZ = posZ;

		dPosX = posX;
		dPosY = posY;
		dPosZ = posZ;
	}

	public Point3D(int posX, int posY, int posZ)
	{
		sPosX = (short) posX;
		sPosY = (short) posY;
		sPosZ = (short) posZ;

		iPosX = posX;
		iPosY = posY;
		iPosZ = posZ;

		fPosX = posX;
		fPosX = posY;
		fPosX = posZ;

		dPosX = posX;
		dPosY = posY;
		dPosZ = posZ;
	}

	public Point3D(float posX, float posY, float posZ)
	{
		sPosX = (short) posX;
		sPosY = (short) posY;
		sPosZ = (short) posZ;

		iPosX = (int) posX;
		iPosY = (int) posY;
		iPosZ = (int) posZ;

		fPosX = posX;
		fPosY = posY;
		fPosZ = posZ;

		dPosX = posX;
		dPosY = posY;
		dPosZ = posZ;
	}

	public Point3D(double posX, double posY, double posZ)
	{
		sPosX = (short) posX;
		sPosY = (short) posY;
		sPosZ = (short) posZ;

		iPosX = (int) posX;
		iPosY = (int) posY;
		iPosZ = (int) posZ;

		fPosX = (float) posX;
		fPosY = (float) posY;
		fPosZ = (float) posZ;

		dPosX = posX;
		dPosY = posY;
		dPosZ = posZ;
	}
	
	public Point3D setPoint(int posX, int posY, int posZ)
	{
		return new Point3D(posX, posY, posZ);
	}

	public Point3D setPoint(float posX, float posY, float posZ)
	{
		return new Point3D(posX, posY, posZ);
	}
	
	public Point3D setPoint(short posX, short posY, short posZ)
	{
		return new Point3D(posX, posY, posZ);
	}
	
	public Point3D setPoint(double posX, double posY, double posZ)
	{
		return new Point3D(posX, posY, posZ);
	}
	
	public static Point3D getNearestPointInList(Point3D refPoint, List<Point3D> pointList)
	{
		Point3D returnPoint = null;
		double lastDistance = 100.0D;
		
		for (Point3D point : pointList)
		{
			double distanceTo = MathHelper.getDistanceToXYZ(refPoint.iPosX, refPoint.iPosY, refPoint.iPosZ, point.iPosX, point.iPosY, point.iPosZ);
			
			if (distanceTo < lastDistance)
			{
				returnPoint = point;
				lastDistance = distanceTo;
			}
		}
		
		return returnPoint;
	}
	
	public Point3D rotate(EnumAxis axis, float angle)
	{
		if (axis == EnumAxis.X)
		{
			return new Point3D(
					dPosX, 
					dPosY * Math.cos(angle) - dPosZ * Math.sin(angle), 
					dPosY * Math.sin(angle) - dPosZ* Math.cos(angle));
		}
		
		else if (axis == EnumAxis.Y)
		{
			return new Point3D(
					dPosZ * Math.sin(angle) - dPosX * Math.cos(angle), 
					dPosY, 
					dPosZ * Math.cos(angle) - dPosX * Math.sin(angle));			
		}
		
		else if (axis == EnumAxis.Z)
		{
			return new Point3D(
					dPosX * Math.cos(angle) - dPosY * Math.sin(angle), 
					dPosX * Math.sin(angle) - dPosY * Math.cos(angle), 
					dPosZ);
		}
		
		else
		{
			return new Point3D(0, 0, 0);
		}
	}
	/**
	 * Gets string representation of the Coordinates object.
	 * 
	 * @return "x, y, z" as string representation of the coordinates stored in this object.
	 */
	@Override
	public String toString()
	{
		return dPosX + ", " + dPosY + ", " + dPosZ;
	}
}
