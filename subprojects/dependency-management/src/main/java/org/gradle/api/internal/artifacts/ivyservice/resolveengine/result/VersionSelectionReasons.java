/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.ivyservice.resolveengine.result;

import org.gradle.api.artifacts.result.ComponentSelectionReason;

public class VersionSelectionReasons {
    public static final ComponentSelectionReason REQUESTED = new DefaultComponentSelectionReason(false, false, false, true, false, "requested");
    public static final ComponentSelectionReason ROOT = new DefaultComponentSelectionReason(false, false, false, true, false, "root");
    public static final ComponentSelectionReason FORCED = new DefaultComponentSelectionReason(true, false, false, false, false, "forced");
    public static final ComponentSelectionReason CONFLICT_RESOLUTION = new DefaultComponentSelectionReason(false, true, false, false, false, "conflict resolution");
    public static final ComponentSelectionReason SELECTED_BY_RULE = new DefaultComponentSelectionReason(false, false, true, false, false, "selected by rule");
    public static final ComponentSelectionReason CONFLICT_RESOLUTION_BY_RULE = new DefaultComponentSelectionReason(false, true, true, false, false, "selected by rule and conflict resolution");
    public static final ComponentSelectionReason COMPOSITE_BUILD = new DefaultComponentSelectionReason(false, false, false, false, true, "composite build substitution");

    public static ComponentSelectionReason withConflictResolution(ComponentSelectionReason reason) {
        if (reason.isConflictResolution()) {
            return reason;
        } else if (reason == SELECTED_BY_RULE) {
            return CONFLICT_RESOLUTION_BY_RULE;
        } else if (reason == REQUESTED) {
            return CONFLICT_RESOLUTION;
        } else if (reason == FORCED) {
            return CONFLICT_RESOLUTION;
        } else if (reason == ROOT) {
            return reason;
        }
        throw new IllegalArgumentException("Cannot create conflict resolution selection reason for input: " + reason);
    }

    private static class DefaultComponentSelectionReason implements ComponentSelectionReason {

        private final boolean forced;
        private final boolean conflictResolution;
        private final boolean selectedByRule;
        private final boolean expected;
        private final boolean compositeParticipant;
        private final String description;

        private DefaultComponentSelectionReason(boolean forced, boolean conflictResolution, boolean selectedByRule, boolean expected, boolean compositeBuild, String description) {
            this.forced = forced;
            this.conflictResolution = conflictResolution;
            this.selectedByRule = selectedByRule;
            this.expected = expected;
            this.compositeParticipant = compositeBuild;
            assert description != null;
            this.description = description;
        }


        public boolean isForced() {
            return forced;
        }

        public boolean isConflictResolution() {
            return conflictResolution;
        }

        public boolean isSelectedByRule() {
            return selectedByRule;
        }

        public boolean isExpected() {
            return expected;
        }

        public boolean isCompositeParticipant() {
            return compositeParticipant;
        }

        public String getDescription() {
            return description;
        }

        public String toString() {
            return description;
        }
    }
}
