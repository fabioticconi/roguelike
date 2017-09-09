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
package com.github.fabioticconi.roguelite.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.DelayedIteratingSystem;
import com.github.fabioticconi.roguelite.components.Position;
import com.github.fabioticconi.roguelite.components.actions.MoveAction;
import com.github.fabioticconi.roguelite.constants.Cell;
import com.github.fabioticconi.roguelite.constants.Side;
import com.github.fabioticconi.roguelite.map.EntityGrid;
import com.github.fabioticconi.roguelite.map.Map;

/**
 * @author Fabio Ticconi
 */
public class MovementSystem extends DelayedIteratingSystem
{
    ComponentMapper<Position>   mPosition;
    ComponentMapper<MoveAction> mMove;

    @Wire
    Map        map;
    @Wire
    EntityGrid grid;

    public MovementSystem()
    {
        super(Aspect.all(Position.class, MoveAction.class));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.artemis.systems.DelayedIteratingSystem#getRemainingDelay(int)
     */
    @Override
    protected float getRemainingDelay(final int entityId)
    {
        return mMove.get(entityId).cooldown;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.artemis.systems.DelayedIteratingSystem#processDelta(int, float)
     */
    @Override
    protected void processDelta(final int entityId, final float accumulatedDelta)
    {
        mMove.get(entityId).cooldown -= accumulatedDelta;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.artemis.systems.DelayedIteratingSystem#processExpired(int)
     */
    @Override
    protected void processExpired(final int entityId)
    {
        final Position   p = mPosition.get(entityId);
        final MoveAction m = mMove.get(entityId);

        mMove.remove(entityId);

        final int newX = p.x + m.direction.x;
        final int newY = p.y + m.direction.y;

        if (!map.isObstacle(newX, newY))
        {
            grid.moveEntity(entityId, p.x, p.y, newX, newY);

            p.x = newX;
            p.y = newY;
        }
        else
        {
            // moving towards a closed door opens it, instead of actually moving
            if (map.get(newX, newY) == Cell.CLOSED_DOOR)
            {
                map.set(newX, newY, Cell.OPEN_DOOR);
            }
        }
    }

    // Public API

    public float moveTo(final int entityId, final float speed, final Side direction)
    {
        final MoveAction m = mMove.create(entityId);

        if (m.direction == direction)
            return m.cooldown;

        m.cooldown = speed;
        m.direction = direction;

        offerDelay(m.cooldown);

        return m.cooldown;
    }
}