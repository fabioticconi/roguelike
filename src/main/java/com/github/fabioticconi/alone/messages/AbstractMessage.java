/*
 * Copyright (C) 2015-2017 Fabio Ticconi
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

package com.github.fabioticconi.alone.messages;

import com.github.fabioticconi.alone.constants.Side;

import java.awt.*;

/**
 * Author: Fabio Ticconi
 * Date: 30/10/17
 */
public abstract class AbstractMessage implements Message
{
    public String actor;
    public String target;
    public int    distance;
    public Side   direction;

    public Color fgCol;
    public Color bgCol;

    public boolean thirdPerson;

    public AbstractMessage()
    {
        this.fgCol = Color.WHITE;
        this.bgCol = Color.BLACK;
    }
}
