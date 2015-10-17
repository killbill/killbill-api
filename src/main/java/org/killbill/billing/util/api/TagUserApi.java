/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.util.api;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.ObjectType;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;
import org.killbill.billing.util.tag.Tag;
import org.killbill.billing.util.tag.TagDefinition;

import static org.killbill.billing.security.Permission.TAG_CAN_ADD;
import static org.killbill.billing.security.Permission.TAG_CAN_CREATE_TAG_DEFINITION;
import static org.killbill.billing.security.Permission.TAG_CAN_DELETE_TAG_DEFINITION;
import static org.killbill.billing.security.Permission.TAG_CAN_DELETE;

public interface TagUserApi extends KillbillApi {

    /**
     * @param context The tenant context
     * @return the list of all available tag definitions
     */
    public List<TagDefinition> getTagDefinitions(TenantContext context);

    /**
     * @param definitionName Identifies the definition.
     * @param description    Describes the use of the definition.
     * @param context        The call context, for auditing purposes
     * @return the newly created tag definition
     * @throws TagDefinitionApiException
     */
    @RequiresPermissions(TAG_CAN_CREATE_TAG_DEFINITION)
    public TagDefinition createTagDefinition(String definitionName, String description, CallContext context) throws TagDefinitionApiException;

    /**
     * @param tagDefinitionId The UUID for that tagDefinition
     * @param context         The call context, for auditing purposes
     * @throws TagDefinitionApiException
     */
    @RequiresPermissions(TAG_CAN_DELETE_TAG_DEFINITION)
    public void deleteTagDefinition(UUID tagDefinitionId, CallContext context) throws TagDefinitionApiException;

    /**
     * @param tagDefinitionId The tag definition id
     * @param context         The call context, for auditing purposes
     * @return The Tag definition
     * @throws TagDefinitionApiException
     */
    public TagDefinition getTagDefinition(UUID tagDefinitionId, TenantContext context) throws TagDefinitionApiException;

    /**
     * @param tageDefinitionName The tag definition name
     * @param context            The call context, for auditing purposes
     * @return the tag definition
     * @throws TagDefinitionApiException
     */
    public TagDefinition getTagDefinitionForName(String tageDefinitionName, TenantContext context) throws TagDefinitionApiException;

    /**
     * @param tagDefinitionIds The collection of the defintion ids
     * @param context          The call context, for auditing purposes
     * @return the tag definition
     * @throws TagDefinitionApiException
     */
    public List<TagDefinition> getTagDefinitions(Collection<UUID> tagDefinitionIds, TenantContext context) throws TagDefinitionApiException;

    /**
     * @param objectId         The id for the object on which to add tags
     * @param objectType       The object type
     * @param tagDefinitionIds The collection of tag definition ids
     * @param context          The call context, for auditing purposes
     * @throws TagApiException
     */
    @RequiresPermissions(TAG_CAN_ADD)
    public void addTags(UUID objectId, ObjectType objectType, Collection<UUID> tagDefinitionIds, CallContext context) throws TagApiException;

    /**
     * @param objectId        The id for the object on which to add tags
     * @param objectType      The object type
     * @param tagDefinitionId The tag definition id
     * @param context         The call context, for auditing purposes
     * @throws TagApiException
     */
    @RequiresPermissions(TAG_CAN_ADD)
    public void addTag(UUID objectId, ObjectType objectType, UUID tagDefinitionId, CallContext context) throws TagApiException;

    /**
     * @param objectId       The id for the object on which to add tags
     * @param objectType     The object type
     * @param tagDefinitions The collection of tag definition ids
     * @param context        The call context, for auditing purposes
     * @throws TagApiException
     */
    @RequiresPermissions(TAG_CAN_DELETE)
    public void removeTags(UUID objectId, ObjectType objectType, Collection<UUID> tagDefinitions, CallContext context) throws TagApiException;

    /**
     * @param objectId        The id for the object on which to add tags
     * @param objectType      The object type
     * @param tagDefinitionId The tage definition id
     * @param context         The call context, for auditing purposes
     * @throws TagApiException
     */
    @RequiresPermissions(TAG_CAN_DELETE)
    public void removeTag(UUID objectId, ObjectType objectType, UUID tagDefinitionId, CallContext context) throws TagApiException;

    /**
     * Find all tags having their object type, associated tag definition name or description matching the search key
     *
     * @param searchKey the search key
     * @param offset    the offset of the first result
     * @param limit     the maximum number of results to retrieve
     * @param context   the user context
     * @return the list of tags matching this search key for that tenant
     */
    public Pagination<Tag> searchTags(String searchKey, Long offset, Long limit, TenantContext context);

    /**
     * @param context the user context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return the list of tags for that tenant
     */
    public Pagination<Tag> getTags(Long offset, Long limit, TenantContext context);

    /**
     * @param objectId        UUID of the object on which to retrieve the tags
     * @param objectType      The type of object
     * @param includedDeleted Whether to include deleted tags
     * @param context         The tenant context
     * @return A map of tag, key being the tagId, and value the tag
     */
    public List<Tag> getTagsForObject(UUID objectId, ObjectType objectType, boolean includedDeleted, TenantContext context);

    /**
     * @param accountId       The account id
     * @param objectType      The type of object on which to retrieve the tags
     * @param includedDeleted Whether to include deleted tags
     * @param context         The tenant context
     * @return A list of tags for that object type and that given account
     */
    public List<Tag> getTagsForAccountType(UUID accountId, ObjectType objectType, boolean includedDeleted, TenantContext context);

    /**
     * @param accountId       The account id
     * @param includedDeleted Whether to include deleted tags
     * @param context         The tenant context
     * @return A list of tags for that given account
     */
    public List<Tag> getTagsForAccount(UUID accountId, boolean includedDeleted, TenantContext context);
}
