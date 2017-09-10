/*
 * Copyright (C) 2017 Fabio Ticconi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.fabioticconi.roguelite.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordsTest
{

    @Test
    public void testDistances() throws Exception
    {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testVisitVonNeumannNeighbours() throws Exception
    {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testVisitMooreNeighbours() throws Exception
    {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testPackUnpackCoords() throws Exception
    {
        final int[] x = { 0, 100, 1000, Integer.MAX_VALUE - 1, Integer.MAX_VALUE };
        final int[] y = { 0, 100, 1000, Integer.MAX_VALUE - 1, Integer.MAX_VALUE };

        for (int i = 0; i < x.length; i++)
        {
            final long key = Coords.packCoords(x[i], y[i]);

            final int[] coords = Coords.unpackCoords(key);

            assertEquals(coords[0], x[i]);
            assertEquals(coords[1], y[i]);
        }
    }
}
