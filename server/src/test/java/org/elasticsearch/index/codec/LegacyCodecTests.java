/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.index.codec;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.test.ESSingleNodeTestCase;

import static org.hamcrest.Matchers.equalTo;

public class LegacyCodecTests extends ESSingleNodeTestCase {

    public void testCanConfigureLegacySettings() {
        assumeTrue("Only when zstd_stored_fields feature flag is enabled", CodecService.ZSTD_STORED_FIELDS_FEATURE_FLAG.isEnabled());

        createIndex("index1", Settings.builder().put("index.codec", "legacy_default").build());
        var codec = client().admin().indices().prepareGetSettings("index1").execute().actionGet().getSetting("index1", "index.codec");
        assertThat(codec, equalTo("legacy_default"));

        createIndex("index2", Settings.builder().put("index.codec", "legacy_best_compression").build());
        codec = client().admin().indices().prepareGetSettings("index2").execute().actionGet().getSetting("index2", "index.codec");
        assertThat(codec, equalTo("legacy_best_compression"));
    }
}
