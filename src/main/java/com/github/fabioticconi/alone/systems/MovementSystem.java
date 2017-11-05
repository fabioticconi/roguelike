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
package com.github.fabioticconi.alone.systems;

import com.artemis.ComponentMapper;
import com.github.fabioticconi.alone.components.Position;
import com.github.fabioticconi.alone.components.Speed;
import com.github.fabioticconi.alone.components.Underwater;
import com.github.fabioticconi.alone.components.actions.ActionContext;
import com.github.fabioticconi.alone.constants.Cell;
import com.github.fabioticconi.alone.constants.Side;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabio Ticconi
 */
public class MovementSystem extends PassiveSystem
{
    static final Logger log = LoggerFactory.getLogger(MovementSystem.class);

    ComponentMapper<Position>   mPosition;
    ComponentMapper<Speed>      mSpeed;
    ComponentMapper<Underwater> mUnderWater;

    StaminaSystem sStamina;
    MapSystem     map;

    public MoveAction move(final int entityId, final Side direction)
    {
        if (direction.equals(Side.HERE))
            return null;

        final MoveAction a = new MoveAction();

        a.actorId = entityId;
        a.direction = direction;

        return a;
    }

    public class MoveAction extends ActionContext
    {
        public Side direction = Side.HERE;

        @Override
        public boolean tryAction()
        {
            if (direction.equals(Side.HERE))
                return false;

            final Position p     = mPosition.get(actorId);
            final Speed    speed = mSpeed.get(actorId);

            final int x2 = p.x + direction.x;
            final int y2 = p.y + direction.y;

            if (!map.isFree(x2, y2))
                return false;

            final Cell cell = map.get(x2, y2);

            switch (cell)
            {
                case HILL:
                case HILL_GRASS:
                    cost = 1.5f;

                    break;

                case MOUNTAIN:
                    cost = 2f;
                    break;

                case HIGH_MOUNTAIN:
                    cost = 3f;
                    break;

                case WATER:
                    if (mUnderWater.has(actorId))
                        cost = 0.25f;
                    else
                        cost = 3f;
                    break;

                case DEEP_WATER:
                    if (mUnderWater.has(actorId))
                        cost = 0.25f;
                    else
                        cost = 4f;
                    break;

                default:
                    cost = 1f;
            }

            delay = speed.value * cost;

            return true;
        }

        @Override
        public void doAction()
        {
            final Position p = mPosition.get(actorId);

            final int x2 = p.x + direction.x;
            final int y2 = p.y + direction.y;

            if (map.items.has(actorId, p.x, p.y))
            {
                // it's a moving item
                map.items.move(p.x, p.y, x2, y2);

                p.x = x2;
                p.y = y2;

                // it doesn't have stamina
            }
            else if (map.obstacles.has(actorId, p.x, p.y) && map.isFree(x2, y2))
            {
                final int id = map.obstacles.move(p.x, p.y, x2, y2);

                if (id >= 0)
                {
                    log.error("entity {} was at the new position {}", id, p);

                    return;
                }

                p.x = x2;
                p.y = y2;

                sStamina.consume(actorId, cost);
            }
        }

        @Override
        public boolean equals(final Object o)
        {
            return super.equals(o) && direction == ((MoveAction) o).direction;
        }
    }
}
