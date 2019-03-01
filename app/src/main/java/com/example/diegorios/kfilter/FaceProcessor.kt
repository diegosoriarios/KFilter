package com.example.diegorios.kfilter

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.otaliastudios.cameraview.CameraView
import com.example.diegorios.kfilter.OverlayView

class FaceProcessor(private val cameraView: CameraView, private val OverlayView: OverlayView) {

    private val options = FirebaseVisionFaceDetectorOptions.Builder()
        .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
        .build()

    private val detector = FirebaseVision.getInstance().getVisionFaceDetector(options)

    fun startProcessing() {
        cameraView.addFrameProcessor { frame ->
            val rotation = frame.rotation / 90

            val metadata = FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setWidth(frame.size.width)
                .setHeight(frame.size.height)
                .setRotation(rotation)
                .build()

            val firebaseVisionImage = FirebaseVisionImage.fromByteArray(frame.data, metadata)

            detector.detectInImage(firebaseVisionImage).addOnSuccessListener { faceList ->
                if(faceList.size > 0) {
                    val face = faceList[0]
                }
            }
        }
    }
}