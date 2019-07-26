/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.Validate;

/**
 * <p>
 * Assists in implementing {@link Diffable#diff(Object)} methods.
 * </p>
 *
 * <p>
 * To use this class, write code as follows:
 * </p>
 *
 * <pre>
 * public class Person implements Diffable&lt;Person&gt; {
 *   String name;
 *   int age;
 *   boolean smoker;
 *
 *   ...
 *
 *   public DiffResult diff(Person obj) {
 *     // No need for null check, as NullPointerException correct if obj is null
 *     return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
 *       .append("name", this.name, obj.name)
 *       .append("age", this.age, obj.age)
 *       .append("smoker", this.smoker, obj.smoker)
 *       .build();
 *   }
 * }
 * </pre>
 *
 * <p>
 * The {@code ToStringStyle} passed to the constructor is embedded in the
 * returned {@code DiffResult} and influences the style of the
 * {@code DiffResult.toString()} method. This style choice can be overridden by
 * calling {@link DiffResult#toString(ToStringStyle)}.
 * </p>
 *
 * @since 3.3
 * @see Diffable
 * @see Diff
 * @see DiffResult
 * @see ToStringStyle
 */
public class DiffBuilder implements Builder<DiffResult> {

    private DiffBuilderPrimitives diffBuilderPrimitives;
	private final Object left;
    private final Object right;
    private final ToStringStyle style;

    /**
     * <p>
     * Constructs a builder for the specified objects with the specified style.
     * </p>
     *
     * <p>
     * If {@code lhs == rhs} or {@code lhs.equals(rhs)} then the builder will
     * not evaluate any calls to {@code append(...)} and will return an empty
     * {@link DiffResult} when {@link #build()} is executed.
     * </p>
     *
     * @param lhs
     *            {@code this} object
     * @param rhs
     *            the object to diff against
     * @param style
     *            the style will use when outputting the objects, {@code null}
     *            uses the default
     * @param testTriviallyEqual
     *            If true, this will test if lhs and rhs are the same or equal.
     *            All of the append(fieldName, lhs, rhs) methods will abort
     *            without creating a field {@link Diff} if the trivially equal
     *            test is enabled and returns true.  The result of this test
     *            is never changed throughout the life of this {@link DiffBuilder}.
     * @throws IllegalArgumentException
     *             if {@code lhs} or {@code rhs} is {@code null}
     * @since 3.4
     */
    public DiffBuilder(final Object lhs, final Object rhs,
            final ToStringStyle style, final boolean testTriviallyEqual) {

    	Validate.isTrue(lhs != null, "lhs cannot be null");
    	Validate.isTrue(rhs != null, "rhs cannot be null");

    	this.diffBuilderPrimitives = new DiffBuilderPrimitives(testTriviallyEqual, lhs, rhs);

        this.left = lhs;
        this.right = rhs;
        this.style = style;
    }

    /**
     * <p>
     * Constructs a builder for the specified objects with the specified style.
     * </p>
     *
     * <p>
     * If {@code lhs == rhs} or {@code lhs.equals(rhs)} then the builder will
     * not evaluate any calls to {@code append(...)} and will return an empty
     * {@link DiffResult} when {@link #build()} is executed.
     * </p>
     *
     * <p>
     * This delegates to {@link #DiffBuilder(Object, Object, ToStringStyle, boolean)}
     * with the testTriviallyEqual flag enabled.
     * </p>
     *
     * @param lhs
     *            {@code this} object
     * @param rhs
     *            the object to diff against
     * @param style
     *            the style will use when outputting the objects, {@code null}
     *            uses the default
     * @throws IllegalArgumentException
     *             if {@code lhs} or {@code rhs} is {@code null}
     */
    public DiffBuilder(final Object lhs, final Object rhs,
            final ToStringStyle style) {

            this(lhs, rhs, style, true);
    }

    /**
     * <p>
     * Test if two {@code boolean}s are equal.
     * </p>
     *
     * @param fieldNam
     *            the field name
     * @param lhs
     *            the left hand {@code boolean}
     * @param rhs
     *            the right hand {@code boolean}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final boolean lhs,
            final boolean rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code boolean[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code boolean[]}
     * @param rhs
     *            the right hand {@code boolean[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final boolean[] lhs,
            final boolean[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code byte}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code byte}
     * @param rhs
     *            the right hand {@code byte}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final byte lhs,
            final byte rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code byte[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code byte[]}
     * @param rhs
     *            the right hand {@code byte[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final byte[] lhs,
            final byte[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code char}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code char}
     * @param rhs
     *            the right hand {@code char}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final char lhs,
            final char rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code char[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code char[]}
     * @param rhs
     *            the right hand {@code char[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final char[] lhs,
            final char[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code double}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code double}
     * @param rhs
     *            the right hand {@code double}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final double lhs,
            final double rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code double[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code double[]}
     * @param rhs
     *            the right hand {@code double[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final double[] lhs,
            final double[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code float}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code float}
     * @param rhs
     *            the right hand {@code float}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final float lhs,
            final float rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code float[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code float[]}
     * @param rhs
     *            the right hand {@code float[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final float[] lhs,
            final float[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code int}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code int}
     * @param rhs
     *            the right hand {@code int}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final int lhs,
            final int rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code int[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code int[]}
     * @param rhs
     *            the right hand {@code int[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final int[] lhs,
            final int[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code long}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code long}
     * @param rhs
     *            the right hand {@code long}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final long lhs,
            final long rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code long[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code long[]}
     * @param rhs
     *            the right hand {@code long[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final long[] lhs,
            final long[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code short}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code short}
     * @param rhs
     *            the right hand {@code short}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final short lhs,
            final short rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code short[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code short[]}
     * @param rhs
     *            the right hand {@code short[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final short[] lhs,
            final short[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code Objects}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code Object}
     * @param rhs
     *            the right hand {@code Object}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final Object lhs,
            final Object rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Test if two {@code Object[]}s are equal.
     * </p>
     *
     * @param fieldName
     *            the field name
     * @param lhs
     *            the left hand {@code Object[]}
     * @param rhs
     *            the right hand {@code Object[]}
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     */
    public DiffBuilder append(final String fieldName, final Object[] lhs,
            final Object[] rhs) {
        return diffBuilderPrimitives.append(fieldName, lhs, rhs, this);
    }

    /**
     * <p>
     * Append diffs from another {@code DiffResult}.
     * </p>
     *
     * <p>
     * This method is useful if you want to compare properties which are
     * themselves Diffable and would like to know which specific part of
     * it is different.
     * </p>
     *
     * <pre>
     * public class Person implements Diffable&lt;Person&gt; {
     *   String name;
     *   Address address; // implements Diffable&lt;Address&gt;
     *
     *   ...
     *
     *   public DiffResult diff(Person obj) {
     *     return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
     *       .append("name", this.name, obj.name)
     *       .append("address", this.address.diff(obj.address))
     *       .build();
     *   }
     * }
     * </pre>
     *
     * @param fieldName
     *            the field name
     * @param diffResult
     *            the {@code DiffResult} to append
     * @return this
     * @throws IllegalArgumentException
     *             if field name is {@code null}
     * @since 3.5
     */
    public DiffBuilder append(final String fieldName,
            final DiffResult diffResult) {
        return diffBuilderPrimitives.append(fieldName, diffResult, this);
    }

    /**
     * <p>
     * Builds a {@link DiffResult} based on the differences appended to this
     * builder.
     * </p>
     *
     * @return a {@code DiffResult} containing the differences between the two
     *         objects.
     */
    @Override
    public DiffResult build() {
        return new DiffResult(left, right, diffBuilderPrimitives.getDiffs(), style);
    }

}
