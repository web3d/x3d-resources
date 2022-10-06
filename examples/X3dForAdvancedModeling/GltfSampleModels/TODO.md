# glTF and X3D examples

## Issues with X3D4 specification

a. Tangent node not yet supported (commented as part of output is OK).
Needed for X3D4.

b. No ImageTexture flipVertically attribute
- Is full glTF conversion possible without it? Yes but with difficulty.
- Time to propose this for X3D4 on mailing list?  Yes, needed.


## Issues with view3dscene conversion

c. Fixed: PBR requires X3D version='4.0'.

d. $ character not allowed in DEF/USE since not part of NMTOKEN type

e. gravityTransform is not a legal attribute in Viewpoint

f. GlamVelvetSofa.x3d has DirectionLight intensity=3 which is out of bounds.
- Should we be changing manually (preferred) or via converter?
- How to post and keep track of comments to glTF Examples?

f. numeric precision of floating point values is extremely large
(but presumably preserves fidelity of original model).  Are these
getting expanded by view3dscene?  Should we have an option to reduce them?


## Issues with X3D metadata in model meta elements

Example additions:

    <meta content='WaterBottle.x3d' name='title'/>
    <meta content='This glTF model is so beautiful on the outside, and wet on the inside etc.' name='description'/>
    <meta content='17 July 2021' name='created'/>
    <meta content='28 September 2021' name='modified'/>
    <meta content='TBD' name='creator'/>
    <meta content='https://github.com/KhronosGroup/glTF-Sample-Models/tree/master/2.0' name='reference'/>
    <meta content='https://khronos/gltf' name='reference'/>
    <meta content='https://en.wikipedia.org/glTF' name='reference'/>
    <meta content='https://www.web3d.org/x3d4' name='reference'/>
    <meta content='https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-CD1' name='reference'/>
    <meta content='https://castle-engine.io/creating_data_model_formats.php#section_gltf' name='reference'/>
    <meta content='view3dscene, https://castle-engine.io/view3dscene.php' name='generator'/>
    <meta content='X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit' name='generator'/>
    <meta content='https://X3dGraphics.com/examples/X3dForAdvancedModeling/GltfSampleModels/WaterBottle.x3d' name='identifier'/>
    <meta content='https://www.web3d.org/x3d/content/examples/license.html' name='license'/>
    
    <WorldInfo title="WaterBottle.x3d" info=''"glTF Sample Model"''/>

Further details:

* https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#meta

# Issues with X3D schema and DTD validation, X3D Tooltips and X3D Schematron

1. Need to add containerFieldChoicesX3DTexture2DNode enumerations: baseTexture,
   emissiveTexture, metallicRoughnessTexture, normalTexture, occlusionTexture
    - fixed in X3D Schema and DOCTYPE, also in X3D Tooltips
    - consider rules for X3D Schematron
