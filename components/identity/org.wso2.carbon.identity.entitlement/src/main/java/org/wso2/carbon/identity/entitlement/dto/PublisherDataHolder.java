/*
*  Copyright (c)  WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.identity.entitlement.dto;

import org.wso2.carbon.registry.core.Resource;

import java.util.*;

/**
 * 
 */
public class PublisherDataHolder {

    public static final String MODULE_NAME = "EntitlementModuleName";

    private String moduleName;

    private PublisherPropertyDTO[] propertyDTOs;

    private StatusHolder[] statusHolders;

    private StatusHolder latestStatus;

    public PublisherDataHolder() {
    }

    public PublisherDataHolder(String moduleName) {
        this.moduleName = moduleName;
    }

    public PublisherDataHolder(Resource resource) {

        List<PublisherPropertyDTO> propertyDTOs = new ArrayList<PublisherPropertyDTO>();
        List<StatusHolder> statusHolders = new ArrayList<StatusHolder>();
        if(resource != null && resource.getProperties() != null){
            Properties properties = resource.getProperties();
            int i = 0;
            for(Map.Entry<Object, Object> entry : properties.entrySet()){
                PublisherPropertyDTO dto = new PublisherPropertyDTO();
                dto.setId((String)entry.getKey());
                Object value = entry.getValue();
                if(value instanceof ArrayList){
                    List list = (ArrayList) entry.getValue();
                    if(list != null && list.size() > 0 && list.get(0) != null){
                        if(((String)entry.getKey()).equals(StatusHolder.STATUS_HOLDER_NAME + i)){
                            StatusHolder statusHolder = new StatusHolder(StatusHolder.TYPE_PUBLISH);
                            if(list.size() > 0  && list.get(0) != null){
                                statusHolder.setTimeInstance((String)list.get(0));
                            }
                            if(list.size() > 1  && list.get(1) != null){
                                statusHolder.setKey((String)list.get(1));
                            }
                            if(list.size() > 2  && list.get(2) != null){
                                statusHolder.setKey((String)list.get(2));
                            }
                            if(list.size() > 3  && list.get(3) != null){
                                statusHolder.setMessage((String)list.get(3));
                                statusHolder.setSuccess(false);
                            } else {
                                statusHolder.setSuccess(true);
                            }
                            statusHolders.add(statusHolder);
                            i++;
                            continue;
                        }

                        dto.setValue((String)list.get(0));

                        if(list.size() > 1  && list.get(1) != null){
                            dto.setDisplayName((String)list.get(1));
                        }
                        if(list.size() > 2  && list.get(2) != null){
                            dto.setDisplayOrder(Integer.parseInt((String)list.get(2)));
                        }
                        if(list.size() > 3  && list.get(3) != null){
                            dto.setRequired(Boolean.parseBoolean((String)list.get(3)));
                        }
                        if(list.size() > 4  && list.get(4) != null){
                            dto.setSecret(Boolean.parseBoolean((String)list.get(4)));
                        }

                        //password must be decrypted TODO
                        if(dto.isSecret()){

                        }
                    }
                }
                if(MODULE_NAME.equals(dto.getId())){
                    moduleName = dto.getValue();
                    continue;
                }

                propertyDTOs.add(dto);
            }
        }

        this.propertyDTOs = propertyDTOs.toArray(new PublisherPropertyDTO[propertyDTOs.size()]);
        this.statusHolders = statusHolders.toArray(new StatusHolder[statusHolders.size()]);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public PublisherPropertyDTO[] getPropertyDTOs() {
        return Arrays.copyOf(propertyDTOs, propertyDTOs.length);
    }

    public void setPropertyDTOs(PublisherPropertyDTO[] propertyDTOs) {
        this.propertyDTOs = Arrays.copyOf(propertyDTOs, propertyDTOs.length);
    }

    public StatusHolder getLatestStatus() {
        return latestStatus;
    }

    public void setLatestStatus(StatusHolder latestStatus) {
        this.latestStatus = latestStatus;
    }

    public StatusHolder[] getStatusHolders() {
        return Arrays.copyOf(statusHolders, statusHolders.length);
    }

    public void addStatusHolders(List<StatusHolder> statusHolders) {
        for(int i =0; i < 10 ; i ++){
            this.statusHolders[i] = statusHolders.get(i);
        }
    }
}