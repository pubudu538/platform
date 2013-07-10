/*
* Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.identity.provider.mgt.ui.client;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.provider.mgt.stub.IdentityProviderMgtExceptionException;
import org.wso2.carbon.identity.provider.mgt.stub.IdentityProviderMgtServiceStub;
import org.wso2.carbon.identity.provider.mgt.stub.dto.TrustedIdPDTO;

import java.rmi.RemoteException;

public class IdentityProviderMgtServiceClient {

    private static Log log = LogFactory.getLog(IdentityProviderMgtServiceClient.class);
    private IdentityProviderMgtServiceStub stub;

    /**
     *
     * @param backendServerURL
     * @param configCtx
     * @throws org.apache.axis2.AxisFault
     */
    public IdentityProviderMgtServiceClient(String cookie, String backendServerURL, ConfigurationContext configCtx) {

        String serviceURL = backendServerURL + "IdentityProviderMgtService";
        try {
            stub = new IdentityProviderMgtServiceStub(configCtx, serviceURL);
        } catch (AxisFault axisFault) {
            log.error("Error while instantiating IdentityProviderMgtServiceStub", axisFault);
        }
        ServiceClient client = stub._getServiceClient();
        Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);
    }

    /**
     * @param tenantDomain
     * @return TrustedIdPDTO
     * @throws IdentityProviderMgtExceptionException
     */
    public TrustedIdPDTO getTenantIdP(String tenantDomain) throws IdentityProviderMgtExceptionException{
        try {
            return stub.getTenantIdP(tenantDomain);
        } catch (RemoteException e) {
            log.error("Error invoking remote service", e);
            throw new IdentityProviderMgtExceptionException("Error invoking remote service");
        }
    }

    /**
     * @param oldTrustedIdPDTO
     * @param newTrustedIdPDTO
     * @throws IdentityProviderMgtExceptionException
     */
    public void updateTenantIdP(TrustedIdPDTO oldTrustedIdPDTO, TrustedIdPDTO newTrustedIdPDTO) throws IdentityProviderMgtExceptionException{
        try {
            stub.updateTenantIdP(oldTrustedIdPDTO, newTrustedIdPDTO);
        } catch (RemoteException e) {
            log.error("Error invoking remote service", e);
            throw new IdentityProviderMgtExceptionException("Error invoking remote service");
        }
    }


}