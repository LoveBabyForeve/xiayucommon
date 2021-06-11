package com.xiayule.commonlibrary.permission;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.xiayule.commonlibrary.R;

import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @Description: 使用EasyPermission请求不同的权限
 * @Author: 下雨了
 * @CreateDate: 2020/5/9 14:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 14:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EasyPermissionUtil {
    private static EasyPermissionUtil easyPermissionUtil;


    public static synchronized EasyPermissionUtil getInstance() {
        if (easyPermissionUtil == null) {
            easyPermissionUtil = new EasyPermissionUtil();
        }
        return easyPermissionUtil;
    }

    /**
     * 请求权限
     *
     * @param context
     * @param permsAgain
     */

    public boolean postPermissions(Context context, @Size(min = 1) @NonNull String... permsAgain) {
        if (EasyPermissions.hasPermissions(context, permsAgain)) {
            return true;
        } else {
            for (String permissions : permsAgain) {
                /*存储权限*/
                if (permissions.equals(EasyPermissionManager.READ_EXTERNAL_STORAGE)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.WRITE_EXTERNAL_STORAGE, EasyPermissionManager.READ_EXTERNAL_STORAGE)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_storage_location_rationale), 0, permsAgain);
                        return false;
                    }
                }
                if (permissions.equals(EasyPermissionManager.WRITE_EXTERNAL_STORAGE)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.WRITE_EXTERNAL_STORAGE, EasyPermissionManager.READ_EXTERNAL_STORAGE)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_storage_location_rationale), 0, permsAgain);
                        return false;
                    }
                }
                /*电话权限*/
                if (permissions.equals(EasyPermissionManager.READ_PHONE_STATE)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.READ_PHONE_STATE)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_read_phone_state), 0, permsAgain);
                        return false;
                    }
                }
                /*相机权限*/
                if (permissions.equals(EasyPermissionManager.CAMERA)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.CAMERA)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_permissions_camera), 0, permsAgain);
                        return false;
                    }
                }
                /*麦克风权限*/
                if (permissions.equals(EasyPermissionManager.RECORD_AUDIO)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.RECORD_AUDIO)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_permissions_audio), 0, permsAgain);
                        return false;
                    }
                }
                /*网络定位*/
                if (permissions.equals(EasyPermissionManager.ACCESS_COARSE_LOCATION)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.ACCESS_COARSE_LOCATION)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_permissions_coarse),
                                0, permsAgain);
                        return false;
                    }
                }
                /*GPS定位*/
                if (permissions.equals(EasyPermissionManager.ACCESS_FINE_LOCATION)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.ACCESS_FINE_LOCATION)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_permissions_fine),
                                0, permsAgain);
                        return false;
                    }
                }
                /* 拨打电话 */
                if (permissions.equals(EasyPermissionManager.CALL_PHONE)) {
                    if (!EasyPermissions.hasPermissions(context, EasyPermissionManager.CALL_PHONE)) {
                        EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.please_permissions_phone),
                                0, permsAgain);
                        return false;
                    }
                }
                /* 需要什么可自行添加 */

            }
            return  false;
        }
    }


    /**
     * 检测授予权限
     *
     * @param requestCode
     * @param perms
     * @param permsAgain
     */
    public boolean grantedPermission(int requestCode, @NonNull List<String> perms, @Size(min = 1) @NonNull String... permsAgain) {
        HashMap<String, Boolean> map = new HashMap<>();
        if (requestCode == 0) {
            if (perms.size() > 0) {
                for (String per : perms) {
                    map.put(per, true);
                }
            }

            if (map.size() == permsAgain.length) {
                boolean isPermissions = false;
                for (boolean b : map.values()) {
                    if (!b) break;
                    isPermissions = true;
                }
                if (isPermissions) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 拒绝权限
     */
    public String deniedPermission(final Context context, int requestCode, @NonNull List<String> perms,  @Size(min = 1) @NonNull final String... permsAgain) {
        String permissionsIntro = "";
        if (requestCode == 0) {
            if (perms.size() > 0) {
                for (String permissions : perms) {
                    /*存储权限*/
                    if (permissions.equals(EasyPermissionManager.WRITE_EXTERNAL_STORAGE) || permissions.equals(EasyPermissionManager.READ_EXTERNAL_STORAGE)) {
                        permissionsIntro = context.getString(R.string.go_storage_location_rationale);
                        break;
                    }
                    /*相机权限*/
                    if (permissions.equals(EasyPermissionManager.CAMERA)) {
                        permissionsIntro = context.getString(R.string.go_please_permissions_camera);
                        break;
                    }
                    /*电话权限*/
                    if (permissions.equals(EasyPermissionManager.READ_PHONE_STATE)) {
                        permissionsIntro = context.getString(R.string.go_please_read_phone_state);
                        break;
                    }
                    /*麦克风权限*/
                    if (permissions.equals(EasyPermissionManager.RECORD_AUDIO)) {
                        permissionsIntro = context.getString(R.string.go_please_permissions_audio);
                        break;
                    }
                    /*网络定位*/
                    if (permissions.equals(EasyPermissionManager.ACCESS_COARSE_LOCATION)) {
                        permissionsIntro = context.getString(R.string.go_please_permissions_coarse);
                        break;
                    }
                    /*GPS定位*/
                    if (permissions.equals(EasyPermissionManager.ACCESS_FINE_LOCATION)) {
                        permissionsIntro = context.getString(R.string.go_please_permissions_fine);
                        break;
                    }
                    /* 拨打电话 */
                    if (permissions.equals(EasyPermissionManager.CALL_PHONE)) {
                        permissionsIntro = context.getString(R.string.go_please_permissions_phone);
                        break;
                    }
                    /* 需要什么可自行添加 */
                }
            }
        }
        return  permissionsIntro;
    }
}
