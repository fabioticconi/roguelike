/**
 * Copyright 2015 Fabio Ticconi
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.fabioticconi.roguelite.components;

import com.artemis.Component;

/**
 * @author Fabio Ticconi
 */
public class Position extends Component
{
    public int x;
    public int y;

    public Position()
    {

    }

    public Position(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    public void set(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return String.format("x: %d, y: %d", x, y);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Position position = (Position) o;

        if (x != position.x)
            return false;
        return y == position.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}